### How to run this

There are 2 components

* Infrastructure services
* Application services

## Run infrastructure

The below will setup the 3 services 

* Eureka Discovery Server - Service Registry
* Spring Config Server - Service Configuration Management
* Zipkin Server - Service Tracing

The Config Server will register with the Eureka Server. Zipkin is a docker image downloaded from the dockerhub.

```
docker-compose -f docker-compose-infra.yml up
```

Test the 3 services

```
http://localhost:8712/eureka
http://localhost:10092/question-ms/default
http://localhost:9412/zipkin
```
## Run the Services

There are 4 micorservices

* Random service
* Question service
* Answer service
* Dispatcher service

All of these microservices will register itself to the Discovery server and is configured to get the configuration from the Configuration server.
these microservices will also be traced using the Zipkin server.
So if you look at the docker-compose.yml file, the 3 core infrastructure services are configured.

```
INFRASTRUCUTRE_SERVER=HOST_NAME_WHERE_INFRA_SERVICES_ARE_RUNNING docker-compose up

eg:- INFRASTRUCUTRE_SERVER=192.168.1.129 docker-compose up
```
