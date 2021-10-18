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

git config --global user.email "gitlab-ci.${CI_PIPELINE_ID}.${CI_JOB_ID}@$(hostname)"
git config --global user.name "GitLab Deployment-Pipeline :)"
git remote set-url origin ${CI_BUILD_REPO}
