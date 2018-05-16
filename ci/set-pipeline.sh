#!/bin/bash
fly -t tools set-pipeline \
    --load-vars-from ${HOME}/.chatter-concourse-credentials.yml \
    -p "chatter-k8s" \
    -c pipeline.yml
