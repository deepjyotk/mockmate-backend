#!/bin/sh
set -e

# Wait for auth-service:8080
./wait-for-it.sh auth-service:8080 -t 60 --strict -- echo "auth-service is up"

# Wait for room-ws-service:9090
./wait-for-it.sh room-ws-service:9090 -t 60 --strict -- echo "room-ws-service is up"

# Start Nginx
exec nginx -g 'daemon off;'
