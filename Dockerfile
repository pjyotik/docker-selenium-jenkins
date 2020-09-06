FROM openjdk:8u191-jre-alpine3.8

# adding curl and jq to image for running health check script
RUN apk add curl jq

# Workspace
WORKDIR /usr/share/selenium-docker

# ADD .jar under target and libs folder from host into this image
ADD target/selenium-docker.jar selenium-docker.jar
ADD target/selenium-docker-tests.jar selenium-docker-tests.jar
ADD target/libs libs

## ADD suite files and if any other dependencies like .csv .json etc
ADD book-flight-module.xml book-flight-module.xml
ADD search-module.xml search-module.xml

#ADD healthcheck script
RUN wget https://s3.amazonaws.com/selenium-docker/healthcheck/healthcheck.sh
#ADD healthcheck.sh healthcheck.sh

# BROWSER
# HUB_HOST
# MODULE
##ENTRYPOINT java -cp selenium-docker.jar:selenium-docker-tests.jar:libs/* -DBROWSER=$BROWSER -DHUB_HOST=$HUB_HOST org.testng.TestNG $MODULE
ENTRYPOINT sh healthcheck.sh