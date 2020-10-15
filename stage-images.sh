echo "$DOCKER_PASSWORD" | docker login -u "$DOCKER_ID" --password-stdin
docker push 1after/eureka-service:latest 
docker push 1after/dispatcher:latest 
docker push 1after/question:latest 
docker push 1after/answer:latest 
docker push 1after/random:latest
docker push 1after/config-server:latest 

docker push 1after/eureka-service:$SHA
docker push 1after/dispatcher:$SHA
docker push 1after/question:$SHA 
docker push 1after/answer:$SHA 
docker push 1after/random:$SHA
docker push 1after/config-server:$SHA 
