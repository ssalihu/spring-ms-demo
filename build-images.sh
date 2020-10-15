SHA=$(git rev-parse HEAD)
docker build -t 1after/eureka-service:latest -t 1after/eureka-service:$SHA ./eureka-service
docker build -t 1after/dispatcher:latest -t 1after/dispatcher:$SHA ./dispatcher
docker build -t 1after/question:latest -t 1after/question:$SHA ./question
docker build -t 1after/answer:latest -t 1after/answer:$SHA ./answer
docker build -t 1after/random:latest -t 1after/random:$SHA ./random
docker build -t 1after/config-server:latest -t 1after/config-server:$SHA ./config-server

