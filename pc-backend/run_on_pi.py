
import RPi.GPIO as GPIO
from flask import Flask, json, render_template
from datetime import datetime, timedelta
import time
import requests
api = Flask(__name__, static_folder='../pc-app/build/static', template_folder='../pc-app/build')

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
    res = requests.get('http://192.168.1.105/stop-parot')
    message = res.content
    
    if(message == b'stoped'):
        resetTimer()
        return 'Stoped parrot playing'
    
    return 'error stopinng parot'


@api.route('/sleepPc', methods=['GET'])
def sleepPc():
    stopParot()
    res = requests.get('http://192.168.1.105/sleep')
    message = res.content
    
    if(message == b'sleeping'):
        return 'sleeping'
    
    return 'error pc not asleep'
    
@api.route('/', methods=['GET'])
def main_shit():
    return render_template("index.html", token="Hello Flask+React")

api.run(host='0.0.0.0', port=80)
destroy()
