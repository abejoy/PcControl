import os
import time
import vlc
import threading
import subprocess
from flask import Flask, json

api = Flask(__name__)
my_song = None

@api.route('/playParot', methods=['GET'])
def playParot():
    global my_song
    my_song = vlc.MediaPlayer("Parot.mp3")
    my_song.play()
    # os.startfile('Parot.mp3')
    return "playing"


@api.route('/stop-parot', methods=['GET'])
def stopParot():
    global my_song
    if(my_song != None):
        my_song.stop()
    return 'stoped'


@api.route('/sleep', methods=['GET'])
def sleepPc():
    def do_work():
        stopParot()
        subprocess.call(['Sleep.bat'])
        time.sleep(1)

    thread = threading.Thread(target=do_work)
    thread.start()

    return 'sleeping'


api.run(host='0.0.0.0', port=5000)
