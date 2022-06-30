cd pc-app
docker rm -f pcapp
docker image rm -f pcapp:prod
docker build -t pcapp:prod .
screen -d -m docker run -it --rm -p 80:80 pcapp:prod --name pcapp
# xterm -e "sudo serve -s build -l 80"
# cd ../pc-backend
# xterm -e "sudo python3 run_on_pi.py"
