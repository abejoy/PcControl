cd pc-app
docker image rm -f pcapp:prod
docker build -t pcapp:prod
xterm -e "docker run -it --rm -p 80:80 pcapp:prod"
# xterm -e "sudo serve -s build -l 80"
# cd ../pc-backend
# xterm -e "sudo python3 run_on_pi.py"
