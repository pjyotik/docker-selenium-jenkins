# Selenium Docker Project

## Terminologies

1. Dockerfile   - Infrastructure as code
2. Build        - Creating an image snapshot from the Dockerfile
3. Image        - VM Snapshot
4. Tag          - Version of Image/release
5. Container    - Lightweight VM, created from a specific image version. We can create multiple containers from the same Image
6. DockerHub    - Image Repository

## Basic docker commands

* $ docker pull image     - pulls an image from DockerHub
* $ docker images         - lists all the images in our machine
* $ docker ps             - lists all the running containers
* $ docker ps -a          - lists all the containers including stopped containers
* $ docker run image      - creates a container from an image
* $ docker stop container id/container name   - stops a running container   
* $ docker system prune -f  - removes all the stopped containers, volumes etc (-f forcefully removes)
* $ docker system prune -a  - removes all stopped containers + unused images

### ImageName format

* $ docker pull [repository-host:port] / [ owner ] / imageName [:tag]
    * Optional
        - repository-host:port
        - owner
        - tag
    * Mandatory
        - imageName

## Docker Run Options

* -d    -   To run the container in the backgound
* -i    -   To attach the standard input to the container
* -t    -   To attach the standard output to the container
* --name somename           -   To assign given name to our container
* --entrypoint=/bin/bash    -   Entrypoint is the command to be executed once a container is created. 
                                An image might have an entrypoint already.
                                This option overrides the container's entrypoint 
* -p 8080:8080              -   To map a host port with a container port
                                -p **host-port:container-port**
* -v /a/b/c:/user/share     -   To map a host directory with a container directory
                                -p host-directory:container-directory

examples: 
    docker run -d imageName
    docker run -it imageName
    docker run -it --entrypoint=/bin/bash imageName
    
* -p    -   port mapping

examples:
    - docker run -p hostPort:containerPort images
    - docker run -p 80:80 pjyotik/jenkins
    - docker run -p 4444:80 mongo
    
* -v    -   volume mapping

examples:
    - docker run -v /path/to/host/dir:/path/to/container/dir images
    - docker run -it -v /run/desktop/mnt/host/c/Workspace/Testing/spring-selenium:/a/b/c ubuntu


## Creating Networks

* $ docker network create test-network  

* $ docker run -d --name=nginx --network=test-network nginx

* $ docker run -it --network=test-network alpine   
  $ wget nginx
  
  
  ## Packaging and Running testNg xml in local selenium-grid
  
  1. Start the Selenium-grid
        $ cd /mnt/c/selenium-grid
        $ docker-compose up -d --scale chrome=2 --scale firefox=2
  
  2. Package the files
        $ mvn clean package -DskipTests
  
  3. Run the modules
        (For running in linux replace ; with : )
        $ java -cp selenium-docker.jar;selenium-docker-tests.jar;libs/* org.testng.TestNG ../book-flight-module.xml
        
        (passing BROWSER parameter to run)
        $ java -cp selenium-docker.jar;selenium-docker-tests.jar;libs/* -DBROWSER=chrome org.testng.TestNG ../search-module.xml

  4. Build the Image after successful run
        $ docker build -t=pkalita/selenium-docker .
        
        (Run the container in interactive mode and check the files)
        $ docker run -it --entrypoint=/bin/sh pkalita/selenium-docker
        
        (add volume and run the test inside)
        $ docker run -it --entrypoint=/bin/sh -v /c/Workspace/Testing/DockerSelenium/output:/usr/share/selenium-docker/test-output pkalita/selenium-docker
        
        $ java -cp selenium-docker.jar:selenium-docker-tests.jar:libs/* org.testng.TestNG book-flight-module.xml
        (added the local host ip address so that it can connect to local running grid)
        $ java -cp selenium-docker.jar:selenium-docker-tests.jar:libs/* -DHUB_HOST=192.168.0.11 org.testng.TestNG book-flight-module.xml
        
        
  5. Run the container by passing the environment variables
        (running without volume mapping)
        $ docker run -e HUB_HOST=192.168.0.11 -e MODULE=book-flight-module.xml pkalita/selenium-docker
        
        (adding volume)
        $ docker run -e HUB_HOST=192.168.0.11 -e MODULE=search-module.xml -v /c/Workspace/Testing/DockerSelenium/output:/usr/share/selenium-docker/test-output pkalita/selenium-docker
        
  ## Running the entire infrastructure using docker-compose (selenium-grid and the tests)
  
  1. Create docker-compose.yml file
  
  2. ADD healthcheck.sh script to verify the nodes are connected to the hub
  
  (To see only search module logs)
  3. docker-compose up | grep -e 'search-module'
  
  
  ## Creating Jenkins Container
  
  (Move to --> C:/Docker)
  1. docker run -p 8080:8080 -p 50000:50000 -v "$PWD/Jenkins_home:/var/jenkins_home" jenkins/jenkins:lts
  
  2. Initial password: 6ae152e5100449e785034a64ee145b21
  
  3. username: admin
     password: admin
     
  4. Create a Slave Node in Jenkins 
     
     - slave node name - Docker1 
     - download the agent jar file and save in /mnt/c/Docker/downloads_agent_jar
     - run the command below:
     $ java -jar agent.jar -jnlpUrl http://localhost:8080/computer/DOCKER1/slave-agent.jnlp -secret 674f2dc3fab4fec49b709fbf651fe5f4970a8dbb2d9aa203a276e8e4966bafac -workDir "/c/Docker/slave_jenkins"
     
     - Remove the master node and add docker credentials 
  
    
   