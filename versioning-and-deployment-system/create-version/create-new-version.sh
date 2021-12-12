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
readonly LOG_PREFIX='[Versioning- and Deployment-System: Version-Creation]: ';

readonly NEW_VERSION="$("${PWD}/versioning-and-deployment-system/create-version/subscripts/create-new-version/determine-new-version.sh")"
readonly COMMIT_MESSAGE_FOR_NEW_VERSION="[Versioning- and Deployment-System] set new Version of Software-Project: \"${NEW_VERSION}\"."
readonly BRANCH_FOR_NEW_VERSION="new-version_${NEW_VERSION}"

# Procedures.
echo "${LOG_PREFIX}Start Creating new Version (\"${NEW_VERSION}\") on new Branch: \"${BRANCH_FOR_NEW_VERSION}\"."

git fetch origin

source "${PWD}/versioning-and-deployment-system/create-version/subscripts/gitlab/setup-gitlab-repository-config.sh"

source "${PWD}/versioning-and-deployment-system/create-version/subscripts/create-new-version/set-new-version-for-maven.sh" "${NEW_VERSION}"

source "${PWD}/versioning-and-deployment-system/create-version/subscripts/git-tools/commit-current-workspace-and-tag-commit.sh" "${COMMIT_MESSAGE_FOR_NEW_VERSION}" "${NEW_VERSION}"

source "${PWD}/versioning-and-deployment-system/create-version/subscripts/git-tools/push-latest-commit-of-current-workspace.sh" "${BRANCH_FOR_NEW_VERSION}"

echo "${LOG_PREFIX}Created new Version of Software-Project on Branch \"${BRANCH_FOR_NEW_VERSION}\": \"${NEW_VERSION}\"."
