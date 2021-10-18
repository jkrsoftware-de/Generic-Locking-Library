#!/bin/bash
###### 🌏 ###### 💬 ######
# All my public-accessible Software-Content is licensed under no License.
# You can use my Code as you want — my only Wish is: mark me as Original-Creator of your Derivation. But you mustn't. :)
#
# My Intention of creating/publishing Free-Software is to help our Public Society.
# In this particular Case our newly-created "Computer-World". I mean everything regarding complex IT-Systems.
#
# made for jkrsoftware.de as Versioning- and Deployment-System.
# made with ❤ by Jeremy Krüger (jkr.one).
###### 🌏 ###### 💬 ######

readonly DATE_OF_LAST_COMMIT="$(git log -1 --date=format:"%Y-%m-%d_%H-%M" --format="%ad")"
readonly SHORT_GIT_COMMIT_ID_OF_LAST_COMMIT="$(git rev-parse --verify HEAD --short)"

echo "${DATE_OF_LAST_COMMIT}_${SHORT_GIT_COMMIT_ID_OF_LAST_COMMIT}"
