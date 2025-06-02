#!/usr/bin/env bash

PRIVATE_KEY_FILE=private.pem
PUBLIC_KEY_FILE=public.pem

openssl genpkey -algorithm RSA -out $PRIVATE_KEY_FILE -pkeyopt rsa_keygen_bits:4096

openssl rsa -in $PRIVATE_KEY_FILE -pubout -out $PUBLIC_KEY_FILE