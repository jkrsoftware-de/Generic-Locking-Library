#!/bin/bash
###### 🌏 ###### 💬 ######
# All my public-accessible Software-Content aren't underlying any Software-License. It's Free-Software.
# You can use my Code as you want — my only Wish is: mark me as Original-Creator of your Derivation. But you mustn't. :)
#
# My Intention of creating/publishing Free-Software is to help our Public Society.
# In this particular Case our newly-created "Computer-World". I mean everything regarding complex IT-Systems.
#
# made for jkrsoftware.de as Versioning- and Deployment-System.
# made with ❤ by Jeremy Krüger (jkr.one). 😊
###### 🌏 ###### 💬 ######

readonly NEW_VERSION_TO_SET=${1}

if [[ -z ${NEW_VERSION_TO_SET} ]]; then
  echo "Can't set a new Version (over Apache's Maven-Release Plugin), cause you don't specified a String for the new one."
  exit 1
fi

mvn clean test verify
mvn versions:set -DnewVersion=${NEW_VERSION_TO_SET}
git add pom.xml
