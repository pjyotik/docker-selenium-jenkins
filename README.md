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
  
  
  