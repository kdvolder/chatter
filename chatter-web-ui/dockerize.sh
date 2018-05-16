#!/bin/bash
set -e
WORKDIR=$(pwd)
./mvnw clean package
cp target/*.jar docker/fatjar.jar
cd docker
docker build -t kdvolder/chatter-web-ui .