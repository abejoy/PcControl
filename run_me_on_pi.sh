cd pc-app
gnome-terminal -e "sudo serve -s build -l 80"
cd ../pc-backend
gnome-terminal -e "sudo python3 run_on_pi.py"