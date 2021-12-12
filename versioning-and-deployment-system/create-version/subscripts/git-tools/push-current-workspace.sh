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
readonly BRANCH_TO_COMMIT=${1}

if [[ -z ${BRANCH_TO_COMMIT} ]]; then
  echo "Can't push the current Workspace to the Remote-Git-Repository, cause you don't provided a Remote-Branch.";
  exit 1
fi

echo "${LOG_PREFIX}Push the current Workspace to the Remote-Git-Repository."
git checkout -b ${BRANCH_TO_COMMIT}
git push origin ${BRANCH_TO_COMMIT} --tags
