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
readonly NEW_VERSION_TO_SET=${1}

# Procedures.
if [[ -z ${NEW_VERSION_TO_SET} ]]; then
  echo "Can't set a new Version (over Apache's Maven-Release Plugin), cause you don't specified, where I should deploy the last Version of the current Git-Workspace."
  echo "If you want to start a Deployment for an older Version, just execute this Script on the particular Version-Workspace."
  echo "The Versioning- and Deployment-System uses Git to describe the latest created Version on the current Git-History."
  exit 1
fi

mvn clean test verify -Dgpg.skip
mvn versions:set "-DnewVersion=${NEW_VERSION_TO_SET}"
git add pom.xml
