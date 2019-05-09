#! /usr/bin/env bash

# This script takes the current value found in /etc/host for open-registry.test
# and replaces it with a new one

TO_REPLACE=$(cat /etc/hosts | grep -i "open-registry.test" | cut -d ' ' -f 1)

NEW_VALUE=$(docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' $(docker ps | grep -i front | cut -d ' ' -f 1))

sudo sed -i "s/$TO_REPLACE/$NEW_VALUE/g" /etc/hosts
