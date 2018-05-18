#!/bin/bash
set -ex
GIT_ROOT=$(pwd)/..
DOCKER_DIR=${GIT_ROOT}/ci/docker

./mvnw clean package
cp target/*.jar ${DOCKER_DIR}/fatjar.jar
cd ${DOCKER_DIR}
docker build -t harbor.tan.springapps.io/chatter/web-ui:latest .
docker push harbor.tan.springapps.io/chatter/web-ui:latest
