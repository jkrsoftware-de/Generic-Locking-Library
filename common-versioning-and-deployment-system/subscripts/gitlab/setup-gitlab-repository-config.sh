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

# Set Commiter's Name and E-Mail.
git config --global user.email "gitlab-ci.${CI_PIPELINE_ID}.${CI_JOB_ID}@$(hostname)"
git config --global user.name "GitLab Deployment-Pipeline :)"
# Set Commiter's Name and E-Mail.

# Set Remote-Git URL.
git remote set-url --push origin "${CI_PROJECT_URL}"
# Set Remote-Git URL.

# Set Remote-Git Credentials. #
git config --global credential.helper store
echo "${GIT_CREDENTIALS_FILE_CONTENT}" > '.git-credentials'
# Set Remote-Git Credentials. #
