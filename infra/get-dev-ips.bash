#! /usr/bin/env bash

set -e

REGISTRY_IP=$(docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' $(docker ps | grep -i app | cut -d ' ' -f 1))
PROM_IP=$(docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' $(docker ps | grep -i "prom/prometheus" | cut -d ' ' -f 1))
IPFS_IP=$(docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' $(docker ps | grep -i ipfs | cut -d ' ' -f 1))
FRONT_IP=$(docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' $(docker ps | grep -i front | cut -d ' ' -f 1))
GRAFANA_IP=$(docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' $(docker ps | grep -i front | cut -d ' ' -f 1))

echo "Registry IP: $REGISTRY_IP"
echo "Prometheus IP: $PROM_IP"
echo "IPFS IP: $IPFS_IP"
echo "Grafana IP: $GRAFANA_IP"
echo "Front IP: $FRONT_IP"
