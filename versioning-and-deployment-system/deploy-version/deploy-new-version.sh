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
readonly LOG_PREFIX='[Versioning- and Deployment-System: Version-Deployment]: '

readonly DEPLOYMENT_VARIANT=${1}

if [[ -z ${DEPLOYMENT_VARIANT} ]]; then
  echo "Can't deploy the current Version, cause you don't specified a Deployment-Variant."
  exit 1
fi

if [ "${DEPLOYMENT_VARIANT}" == "MAVEN_DEPLOYMENT" ]; then
  "${PWD}/versioning-and-deployment-system/deploy-version/deployment-variants/maven-deployment.sh" "${2}"
elif [ "${DEPLOYMENT_VARIANT}" == "GIT_DEPLOYMENT" ]; then
  echo "${LOG_PREFIX}This Deployment-Variant is not implemented yet."
fi
elif [ "${DEPLOYMENT_VARIANT}" == "SSH_DEPLOYMENT" ]; then
  echo "${LOG_PREFIX}This Deployment-Variant is not implemented yet."
fi
