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
git config --global user.email "versioning-and-deployment-system.${CI_PIPELINE_ID}.${CI_JOB_ID}@$(hostname)"
git config --global user.name "Versioning- and Deployment-System."
git remote set-url --push origin "${GIT_REPOSITORY_URL_WITH_CREDENTIALS}"
