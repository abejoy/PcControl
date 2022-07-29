prev_version=0.0.4
version=0.0.5

cd ../pc-app
npm i
sudo rm -R ../backend/src/main/resources/static/*
npm run build
sudo cp -R build/ ../backend/src/main/resources/static/

cd ../backend
docker image rm -f abrahamjoys98/lkcyl:$prev_version
sudo mvn clean package
docker build -t pibackend:prod .
docker tag pibackend:prod abrahamjoys98/lkcyl:$version
docker push abrahamjoys98/lkcyl:$version


bash -c "ssh root@www.lkcyl.com 'cd /; docker rm -f pibackend; docker image rm abrahamjoys98/lkcyl:$prev_version; docker run -d --name pibackend -v $(pwd):/home/docker/data -p 80:80 abrahamjoys98/lkcyl:$version'"

