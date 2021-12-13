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
readonly GIT_URL_WITH_CREDENTIALS=${1}
readonly BRANCH_TO_DEPLOY=${2}

# Procedures.
if [[ -z ${GIT_URL_WITH_CREDENTIALS} ]]; then
  echo "You must provide an URL (with Authorization) in order to fulfill a Git-Deployment."
  exit 1
fi
if [[ -z ${BRANCH_TO_DEPLOY} ]]; then
  echo "You must specify an Branch, where the current HEAD should be deployed."
  exit 1
fi

git config --global user.email "versioning-and-deployment-system.${CI_PIPELINE_ID}.${CI_JOB_ID}@$(hostname)"
git config --global user.name "Versioning- and Deployment-System."
git remote rm git-clone
git remote add git-clone "${GIT_URL_WITH_CREDENTIALS}"

git fetch git-clone
git push git-clone "HEAD:${BRANCH_TO_DEPLOY}" -f --tags
