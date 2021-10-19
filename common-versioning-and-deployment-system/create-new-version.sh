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

## Determine new Version and Branch-Name of the new Version. ##
readonly NEW_VERSION="$("${PWD}/common-versioning-and-deployment-system/subscripts/create-new-version/determine-new-version.sh")"
readonly BRANCH_FOR_NEW_VERSION="new-version_${NEW_VERSION}"
## Determine new Version and Branch-Name of the new Version. ##

## Log- and Commitment-Message-Templates. ##
readonly LOG_PREFIX='[GitLab-Deployment: Version-Creation] ';
readonly COMMIT_MESSAGE_FOR_NEW_VERSION="[Versioning- and Deployment-System] Set new Version: \"${NEW_VERSION}\"."
## Log- and Commitment-Message-Templates. ##

## Logic for New-Version-Creation. ##
echo "${LOG_PREFIX}Start Creating new Version (\"${NEW_VERSION}\") on new Branch: \"${BRANCH_FOR_NEW_VERSION}\"."

git fetch origin

source "${PWD}/common-versioning-and-deployment-system/subscripts/gitlab/setup-gitlab-repository-config.sh"

source "${PWD}/common-versioning-and-deployment-system/subscripts/create-new-version/set-new-version-for-maven.sh" "${NEW_VERSION}"

source "${PWD}/common-versioning-and-deployment-system/subscripts/git-tools/commit-current-workspace-and-tag-commit.sh" "${COMMIT_MESSAGE_FOR_NEW_VERSION}" "${NEW_VERSION}"

source "${PWD}/common-versioning-and-deployment-system/subscripts/git-tools/push-latest-commit-of-current-workspace.sh" "${BRANCH_FOR_NEW_VERSION}"

echo "${LOG_PREFIX}Created new Version (\"${NEW_VERSION}\") on Branch: \"${BRANCH_FOR_NEW_VERSION}\"."
## Logic for New-Version-Creation. ##
