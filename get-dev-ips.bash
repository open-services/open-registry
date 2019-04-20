#! /usr/bin/env bash

REPL_IP=$(docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' $(docker ps | grep -i clojure | cut -d ' ' -f 1))
PROM_IP=$(docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' $(docker ps | grep -i prometheus | cut -d ' ' -f 1))
IPFS_IP=$(docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' $(docker ps | grep -i ipfs | cut -d ' ' -f 1))

echo "nRepl IP: $REPL_IP"
echo "Prometheus IP: $PROM_IP"
echo "IPFS IP: $IPFS_IP"
