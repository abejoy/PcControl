import os
import time
import threading
import subprocess
from flask import Flask, json

api = Flask(__name__)


@api.route('/sleep', methods=['GET'])
def sleepPc():
    os.system("$echo sumasuja | sudo -S systemctl suspend")
    return 'sleeping'


api.run(host='0.0.0.0', port=5000)
