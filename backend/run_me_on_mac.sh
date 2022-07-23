cd ../pc-app
npm i
rm -R ../backend/src/main/resources/static/*
npm run build
cp -R build/ ../backend/src/main/resources/static/

cd ../backend
docker image rm -f abrahamjoys98/lkcyl:0.0.3
sudo mvn clean package
docker build -t pibackend:prod .
docker tag pibackend:prod abrahamjoys98/lkcyl:0.0.4
docker push abrahamjoys98/lkcyl:0.0.4