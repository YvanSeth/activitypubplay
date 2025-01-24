#!/bin/env bash
#
# script to quickly generate a self-signed cert
KEYPATH=src/main/resources/keys
ALIAS=activitypubbot
mkdir -p $KEYPATH
keytool -genkeypair -alias $ALIAS -keyalg RSA -keysize 2048 -storetype PKCS12 -keystore $KEYPATH/$ALIAS.p12 -validity 3650

