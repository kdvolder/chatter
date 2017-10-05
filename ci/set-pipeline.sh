#!/bin/bash
fly -t tools set-pipeline \
    --load-vars-from ${HOME}/.chatter-concourse-credentials.yml \
    -p "chatter-toronto-demo" \
    -c pipeline.yml
