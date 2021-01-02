

import RPi.GPIO as GPIO
from flask import Flask, json, render_template, request
from flask_cors import CORS, cross_origin
from datetime import datetime, timedelta
import time
import requests
import os
import smtplib
from email.message import EmailMessage


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
    return os.popen("sudo systemctl is-active jellyfin").read() == "active"

def startJeljyFin():
    if(not isJellyFinRunning()):
        os.system("sudo systemctl start jellyfin")

def stopJellyFin():
    if(isJellyFinRunning()):
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

    if(name == "Start AbeFlix"):
        startJeljyFin()
        return "AbeFlix should be Running, visit www.abrahamjoys.com:8096 please remember to stop by typing Stop AbeFlix"


    if(name == "Stop AbeFlix"):
        stopJellyFin()
        return "AbeFlix should have stoped"

    email = content['contactEmail']
    subject = content['contactSubject']
    message = content['contactMessage']
    msg = EmailMessage()
    msg2 = EmailMessage()


    msg['Subject'] = subject
    msg['From'] = myemail
    msg['To'] = myemail
    msg.set_content('Someone tried to contact you name: ' + name + ' email: '+ email + ' message: ' + message)

    msg2['Subject'] = subject
    msg2['From'] = myemail
    msg2['To'] = email
    msg2.set_content('Thankyou for getting in contact with me ' + name + ' Abraham will get back to you')


    with smtplib.SMTP('smtp.gmail.com', 587) as smtp:
        smtp.ehlo()
        smtp.starttls()
        smtp.ehlo()
        smtp.login("abrahamjoys98@gmail.com", "bdibeluekfavdgpp")
        smtp.send_message(msg)
        smtp.send_message(msg2)

    return 'OK'
    

api.run(host='0.0.0.0', port=8080)
destroy()
