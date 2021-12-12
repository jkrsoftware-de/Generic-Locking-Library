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
readonly LOG_PREFIX='[Versioning- and Deployment-System: Version-Deployment]: ';

readonly SYSTEM_TO_DEPLOY=${1}

if [[ -z ${SYSTEM_TO_DEPLOY} ]]; then
  echo "Can't deploy the current Version, cause you don't specified an System, where I should deploy it.";
  exit 1
fi
