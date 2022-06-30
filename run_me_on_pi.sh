cd pc-app
docker rm -f pcapp
docker image rm -f pcapp:prod
docker build -t pcapp:prod .
screen -d -m docker run -it --rm --name pcapp -p 80:80 pcapp:prod
# sudo serve -s build -l 80
cd ../pi-backend
screen -d -m sudo python3 run_on_pi.py
