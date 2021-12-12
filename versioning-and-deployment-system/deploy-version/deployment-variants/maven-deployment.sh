#!/bin/bash
###### 🌏 ###### 💬 ######
# All my public-accessible Software-Content aren't underlying any Software-License. It's Free-Software.
# You can use my Code as you want — my only Wish is: mark me as Original-Creator of your Derivation. But you mustn't. :)
#
# My Intention of creating/publishing Free-Software is to help our Public Society.
# In this particular Case our newly-created "Computer-World". I mean everything regarding complex IT-Systems.
#
# made with ❤ by Jeremy Krüger (jkr.one). 😊
###### 🌏 ###### 💬 ######
# Variables.
readonly LOG_PREFIX='[Versioning- and Deployment-System: Version-Deployment: Maven-Deployment]: '

readonly PATH_TO_CUSTOM_M2_SETTINGS_FILE=${1}

if [[ -n ${PATH_TO_CUSTOM_M2_SETTINGS_FILE} ]]; then
  cp "${PATH_TO_CUSTOM_M2_SETTINGS_FILE}" "${HOME}/.m2/settings.xml"
fi

echo "${LOG_PREFIX}Start Maven-Deployment."
mvn clean compile verify deploy
echo "${LOG_PREFIX}Maven-Deployment finished."