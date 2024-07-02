#!/usr/bin/env bash

KEYSTORE_PASSWORD=$1

HTTPS_SERVER_KEYSTORE_FILE=https-server.p12
HTTPS_SERVER_KEYSTORE_ALIAS=https-server

CERT_TEMP_FILE=certificat-temp.pem

TRUSTSTORE_FILE=truststore-server.p12

keytool -genkey -alias "$HTTPS_SERVER_KEYSTORE_ALIAS" \
  -keyalg RSA -keysize 4096 \
  -sigalg SHA256withRSA \
  -keystore "$HTTPS_SERVER_KEYSTORE_FILE" \
  -storetype PKCS12 \
  -storepass "$KEYSTORE_PASSWORD" \
  -validity 3650 \
  -ext san=ip:127.0.0.1,dns:localhost

keytool -exportcert -keystore "$HTTPS_SERVER_KEYSTORE_FILE" \
  -alias "$HTTPS_SERVER_KEYSTORE_ALIAS" \
  -storepass "$KEYSTORE_PASSWORD" \
  -storetype PKCS12 \
  -rfc -file "$CERT_TEMP_FILE"

keytool -import -trustcacerts -file "$CERT_TEMP_FILE" \
  -alias "$HTTPS_SERVER_KEYSTORE_ALIAS" \
  -storepass "$KEYSTORE_PASSWORD" \
  -keystore "$TRUSTSTORE_FILE" \
  -srcstoretype PKCS12 \
  -deststoretype PKCS12

rm "$CERT_TEMP_FILE"

###### https://www.baeldung.com/java-https-client-certificate-authentication