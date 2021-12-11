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

readonly BRANCH_TO_COMMIT=${1}

if [[ -z ${BRANCH_TO_COMMIT} ]]; then
  echo "Can't push the latest Commit (of the current Workspace) to the Remote-Repository, cause you don't provided a Remote-Branch.";
  exit 1
fi

readonly LAST_COMMIT_ID="$(git rev-parse --verify HEAD)"

echo "${LOG_PREFIX}Push the latest Commit (of the current Workspace) to the Git-Repository."
git checkout -b ${BRANCH_TO_COMMIT}
git push --tags
git push origin ${LAST_COMMIT_ID}:${BRANCH_TO_COMMIT} --tags
