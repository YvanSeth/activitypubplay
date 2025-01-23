#!/bin/env bash

mkdir -p keys
keytool -genkeypair -alias activitypubbot -keyalg RSA -keysize 2048 -storetype PKCS12 -keystore keys/actibitypubbot.p12 -validity 3650

