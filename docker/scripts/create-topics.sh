#!/bin/bash

set -e

echo "Waiting for Kafka..."

until kafka-topics \
    --bootstrap-server kafka:29092 \
    --list >/dev/null 2>&1
do
    sleep 2
done

echo "Kafka Ready."

create_topic() {

    kafka-topics \
      --bootstrap-server kafka:29092 \
      --create \
      --if-not-exists \
      --topic "$1" \
      --partitions 3 \
      --replication-factor 1

}

create_topic notification.v1.created

create_topic notification.v1.retry

create_topic notification.v1.dlq

echo "Topics created successfully."