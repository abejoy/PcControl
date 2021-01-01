cd pc-app
xterm -e "sudo serve -s build -l 80"
cd ../pc-backend
xterm -e "sudo python3 run_on_pi.py"
