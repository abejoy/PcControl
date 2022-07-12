docker rm -f pibackend
docker image rm -f pibackend:prod
sudo mvn clean package
docker build -t pibackend:prod .
docker run -d --name pibackend -v $(pwd):/home/docker/data -p 8081:8081 pibackend:prod


cd ../pc-app
docker rm -f pcapp
docker image rm -f pcapp:prod
sudo docker build -t pcapp:prod .
screen -d -m docker run -it --rm --name pcapp -p 80:80 pcapp:prod
# sudo serve -s build -l 80
