

import RPi.GPIO as GPIO
from flask import Flask, json, render_template, request
from flask_cors import CORS, cross_origin
from datetime import datetime, timedelta
import time
import requests
import os
import smtplib
from datetime import date

from email.message import EmailMessage
from spreadsheet_writer import addRow


api = Flask(__name__)
CORS(api)


servoPin = 12

presscount = 0

sleeping = False

timer = None

def setup():
    global p
    GPIO.setmode(GPIO.BOARD)
    GPIO.setup(servoPin, GPIO.OUT)
    p = GPIO.PWM(servoPin, 50)
    p.start(0)

def change_angle(angle):
    p.ChangeDutyCycle(2+(angle/18))
    time.sleep(0.5)
    p.ChangeDutyCycle(0)

def destroy():
    p.stop()
    GPIO.cleanup()

def isJellyFinRunning():
    return "active" in os.popen("sudo systemctl is-active jellyfin").read()

def startJeljyFin():
    if(not isJellyFinRunning()):
        os.system("sudo systemctl start jellyfin")

def stopJellyFin():
    if(isJellyFinRunning()):
        print("i am about to stop")
        os.system("sudo systemctl stop jellyfin")

setup()


@api.route('/set-sleep-status/<status>', methods=['GET'])
def set_sleep_status(status):
    global sleeping
    sleeping = status
    return "status = " + str(sleeping) 

@api.route('/get-sleep-status', methods=['GET'])
def get_sleep_status():
    return str(sleeping)


@api.route('/angle/<anglenum>', methods=['GET'])
def angle(anglenum):
    global presscount
    change_angle(int(anglenum))
    time.sleep(0.5)
    change_angle(0)
    presscount += 1
    return "rotated " + anglenum + " degrees, press count = " + str(presscount)


@api.route('/press', methods=['GET'])
def press():
    global presscount, sleeping
    change_angle(130)
    time.sleep(0.5)
    change_angle(0)
    presscount += 1
    sleeping = False
    return "Pc should be on now, attempt: " + str(presscount)

@api.route('/playParot', methods=['GET'])
def playParot():
    global timer
    res = requests.get('http://192.168.1.105/playParot')
    timer = datetime.now() + timedelta(minutes = 30)
    return res.content


@api.route('/parot-time-info', methods=['GET'])
def gettimeinfo():
    global timer
    if(timer == None):
        return '0'
    difference = timer - datetime.now()
    minutes = difference.seconds / 60
    if(minutes > 0 and minutes < 30):
        return str(minutes)
    return '0'

@api.route('/reset-timer', methods=['GET'])
def resetTimer():
    global timer
    timer = None
    return 'timer reset'


@api.route('/stopParot', methods=['GET'])
def stopParot():
    res = requests.get('http://192.168.0.6:5000/stop-parot')
    message = res.content
    
    if(message == b'stoped'):
        resetTimer()
        return 'Stoped parrot playing'
    
    return 'error stopinng parot'


@api.route('/sleepPc', methods=['GET'])
def sleepPc():
    global sleeping
    stopParot()
    res = requests.get('http://192.168.0.6:5000/sleep')
    message = res.content
    
    if(message == b'sleeping'):
        sleeping = True
        return 'sleeping'
    
    return 'error pc not asleep'


@api.route('/contact-form', methods=['POST'])
def contactMe():


    myemail = 'abrahamjoys98@gmail.com'
    content = request.get_json()
    name = content['contactName']
    email = content['contactEmail']
    phone = content['contactPhone']
    parentPhone = content['parentPhone']
    dob = content['dob']
    age = content['age']
    unit = content['unit']
    message = content['contactMessage']

    row = [name, email, phone, parentPhone, dob, age, date.today(), unit]

    addRow(row)

    msg = EmailMessage()
    msg2 = EmailMessage()

    subject = "LKCYL camp donotreply"


    msg['Subject'] = subject
    msg['From'] = myemail
    msg['To'] = "jesvinjoril98@yahoo.co.in"
    msg.set_content('Yo Albert,\nprobably wrorth putting this email spam or a seprate folder as you can get bombarded with this email every time someone registers\nalso check the attachment for the spreadsheet of the participants so far \n the latest entry is: ' + name + ' email: '+ email + ' message: ' + message + ' phone: ' + phone + ' parent phone: ' + parentPhone + 'Date of Birth: ' + dob + ' unit: ' + unit)
    with open('test.xls', 'rb') as content_file:
        content = content_file.read()
        msg.add_attachment(content, maintype='application', subtype='xls', filename='registered_camp_members.pdf')

    msg2['Subject'] = subject
    msg2['From'] = myemail
    msg2['To'] = email
    msg2.set_content('Dear ' + name + '\n Thankyou for registering for LKCYL camp, inorder to complete your registration please send a deposit of £25')


    with smtplib.SMTP('smtp.gmail.com', 587) as smtp:
        smtp.ehlo()
        smtp.starttls()
        smtp.ehlo()
        smtp.login(myemail, "usseymmoqlsvrija")
        smtp.send_message(msg)
        smtp.send_message(msg2)

    return 'Your message was sent, thank you!'
    

api.run(host='0.0.0.0', port=8081)
destroy()
