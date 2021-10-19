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

readonly COMMIT_MESSAGE=${1}
readonly GIT_TAG=${2}

if [[ -z ${COMMIT_MESSAGE} ]]; then
  echo "Can't commit (and tag the newly-created Commit) of the current Git-Workspace, cause you don't provided a Commit-Message.";
  exit 1
fi

if [[ -z ${GIT_TAG} ]]; then
  echo "Can't commit (and tag the newly-created Commit) of the current Git-Workspace, cause you don't provided a Git-Tag to set.";
  exit 1
fi

echo "${LOG_PREFIX}Commit everything of the current Workspace."
git commit -m "${COMMIT_MESSAGE}"

echo "hey"

readonly COMMIT_ID_OF_LAST_COMMIT="$(git rev-parse --verify HEAD)"

echo "hey22 ${COMMIT_ID_OF_LAST_COMMIT}"
git tag -a "${GIT_TAG}" "${COMMIT_ID_OF_LAST_COMMIT}" -m "${COMMIT_MESSAGE}"
