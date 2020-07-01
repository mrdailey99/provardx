## Pre-Build:
## (Optional Execution License): 
## Make a copy of your local .licenses/MYLICENSE.properties file
## Replace the existing key with your execution-only license key 
## Create a .licenses folder in your test project directory and place the execution-only properties file there
## (Optional SMTP Configuration): 
## Ensure you have setup an email account for sending emails through Provar. This is done via the email configuration tab 
## on the ANT build screen. 
## Copy the contents of your $USER_HOME/Provar/.smtp folder to your test project directory.
## (Run script): Make sure runProvarTests.sh is present in the test project directory.

## Build: 
## > cd <directory containing Dockerfile/script/.testproject>
## > docker build -t MyImageName .
## Run: > docker run -it MyImageName
## Get inside container: > docker run -it --entrypoint "/bin/sh" MyImageName
## Enter "bash" (to enter standard terminal)

## Global args (persist through multi-stage builds) can be passed in via CLI or set here
## This version will always be the latest version Provar has made publicly available.
## PROVAR_DEFAULT_VERSION is the full version name. This is either X.X.X.XX or latest.
ARG PROVAR_DEFAULT_VERSION=latest
## PROVAR_MAJOR_VERSION is the major version of Provar, or the monthly release. This is either X.X.X or latest.
ARG PROVAR_MAJOR_VERSION=latest
## This should be your project's name 
ARG PROJECT_NAME=ProvarProject
## Email Build Report Target (remember to set this if you wish to email reports as part of your build)
ARG EMAIL_TARGET
## Environment Level (Provar Test Environment)
ARG ENV
## Test Plan to target in build file
ARG TEST_PLAN
## Provar secrets password (input either here or as "--build-arg PROVAR_SECRETS_PASSWORD=YOURSECRETSPASSWORD" in build command)
ARG ProvarSecretsPassword
## Salesforce Connection Name
ARG PROVAR_sf_Admin
## Salesforce Connection Password
ARG PROVAR_sf_Admin_password
## The username sfdx will use to authenticate to the dev hub for Salesforce and create scratch orgs
ARG DEV_HUB_USERNAME=michael.dailey@brave-otter-7uycux.com
## The alias used for the dev hub for sfdx
ARG DEV_HUB_ALIAS=TrailheadHub
## Consumer Key for dev hub authentication
ARG CONSUMER_KEY=3MVG9_XwsqeYoueLvGVFqnnMhtgExKjNBKqwww4noS7CNe8B296lnbmBDrvUFNKAa9AufbsfavQChFFPbJWzf
## The alias for the scratch org (doesn't need to be unique)
ARG SCRATCH_ORG_ALIAS=ProvarDX
## Dev hub instance URL
ARG INSTANCE_URL="https://brave-otter-7uycux-dev-ed.my.salesforce.com"
## ProvarDX Property File used to run tests
ARG PROVARDX_PROPERTY_FILE=provardx-properties-docker.json

## This docker build assumes you run as root (-u root)
## Initial stage to build from to get openjdk-8 and ANT installed
FROM ubuntu:16.04
LABEL maintainer="Provar Testing <support@provartesting.com>"
LABEL version="1.0"
ARG PROVAR_DEFAULT_VERSION
ARG PROVAR_MAJOR_VERSION
ARG PROJECT_NAME
ARG EMAIL_TARGET
ARG ENV
ARG ANT_TARGET
ARG TEST_PLAN
ARG BUILD_FILE
ARG ProvarSecretsPassword
ARG PROVAR_sf_Admin
ARG PROVAR_sf_Admin_password
ARG DEV_HUB_USERNAME
ARG DEV_HUB_ALIAS
ARG CONSUMER_KEY
ARG SCRATCH_ORG_ALIAS
ARG INSTANCE_URL
ARG PROVARDX_PROPERTY_FILE
# The location to save the Provar binaries to (from downloads page)
ENV REPO_HOME=/srv/Provar \
    PROVAR_VERSION=${PROVAR_DEFAULT_VERSION} \
    ## Set the WORKSPACE for execution
    WORKSPACE=/home/${PROJECT_NAME} \
    DISPLAY=:99.0 \
    JAVA_ARGS=-verbose:class \
    ENVIRONMENT=${ENV} \
    ANT_TARGET=${ANT_TARGET} \
    EMAIL_TARGET=${EMAIL_TARGET} \
    TEST_PLAN=${TEST_PLAN} \
    BUILD_FILE=${BUILD_FILE} \
    ProvarSecretsPassword=${ProvarSecretsPassword} \
    PROVAR_sf_Admin=${PROVAR_sf_Admin} \
    PROVAR_sf_Admin_password=${PROVAR_sf_Admin_password} \
    DEV_HUB_USERNAME=${DEV_HUB_USERNAME} \
    DEV_HUB_ALIAS=${DEV_HUB_ALIAS} \
    SCRATCH_ORG_ALIAS=${SCRATCH_ORG_ALIAS} \
    INSTANCE_URL=${INSTANCE_URL} \
    PROVARDX_PROPERTY_FILE=${PROVARDX_PROPERTY_FILE}

RUN set -ex \
    && apt -y update -qq && apt install -y \
    xvfb \ 
    wget \
    curl \
    git \
    sudo \
    build-essential \
    openjdk-8-jdk \
    ant \
    unzip \
    # Install NodeJS and NPM
    && curl -sL https://deb.nodesource.com/setup_12.x | sudo -E bash - \
    && apt install -y nodejs \
    # Install latest chrome release
    && wget -q -O - https://dl-ssl.google.com/linux/linux_signing_key.pub | apt-key add - \
    && echo "deb [arch=amd64] http://dl.google.com/linux/chrome/deb/ stable main" >> /etc/apt/sources.list.d/google.list \
    && apt update -qq \
    && apt install -qq -y google-chrome-stable google-chrome-beta \
    && wget -O /etc/init.d/xvfb https://gist.githubusercontent.com/axilleas/3fc13e0c90ad9f58bee903a41e8a6d48/raw/169a60010635e05eaa902c5f3b4393321f2452f0/xvfb \
    && chmod 0755 /etc/init.d/xvfb \
    && sh -e /etc/init.d/xvfb start \
    # Install latest Provar libraries
    && mkdir -p ${REPO_HOME}/Provar_ANT_${PROVAR_DEFAULT_VERSION} \
    && wget -qP ${REPO_HOME} https://download.provartesting.com/${PROVAR_MAJOR_VERSION}/Provar_ANT_${PROVAR_DEFAULT_VERSION}.zip \
    && unzip ${REPO_HOME}/Provar_ANT_${PROVAR_DEFAULT_VERSION}.zip -d ${REPO_HOME}/Provar_ANT_${PROVAR_DEFAULT_VERSION} \
    && rm -rf ${REPO_HOME}/Provar_ANT_${PROVAR_DEFAULT_VERSION}.zip \
    # Retrieve ProvarDX plugin from repo
    && git clone --single-branch --branch dev https://github.com/mrdailey99/provardx.git /home/ \
    && cp -r /home/ProvarProject/provardx ${REPO_HOME}/Provar_ANT_${PROVAR_VERSION}  \
    && ant -version \
    && javac -version \
    && node --version \
    && npm --version \
    ## Set up project payload && Copy script to PATH
    && mkdir -p ${WORKSPACE}/ANT/Results \
    && mkdir -p ${WORKSPACE}/src \
    && mkdir -p ${WORKSPACE}/lib \
    && mkdir -p ${WORKSPACE}/bin \
    # Remove additional packages 
    && apt remove -y git \
    curl \
    wget 

COPY assets/server.key /home/assets/server.key
COPY ProvarProject/.secrets /home/ProvarProject/.secrets

# # Install yarn, sfdx-cli and setup ProvarDX plugin 
RUN set -ex \
    && npm install sfdx-cli --global \
    && npm install yarn --global \
    && cd /home/com.provar.plugins.provardx \
    && sfdx plugins:link \
    && yarn run prepack 

# Setup scratch org project to deploy metadata
RUN set -ex \
    && cd /home/ \
    && sfdx force:project:create -n ProvarDX \
    && cp /home/project-scratch-def.json /home/ProvarDX/config/project-scratch-def.json \
    && cp /home/package.xml /home/ProvarDX/package.xml \
    && cp /home/.forceignore /home/ProvarDX/.forceignore 

RUN set -ex \
    # Authorize dev hub to generate scratch orgs
    && sfdx force:auth:jwt:grant --clientid ${CONSUMER_KEY} --jwtkeyfile "/home/assets/server.key" --username ${DEV_HUB_USERNAME} --setdefaultdevhubusername \
    && sfdx force:org:list --clean \
    # Generate a random unique scratch org alphanumeric user (16 chars)
    && export SCRATCH_ORG_USERNAME=`cat /dev/urandom | tr -dc 'a-zA-Z0-9' | fold -w 16 | head -n 1`@testing.com \
    # Create scratch org
    && sfdx force:org:create -f /home/ProvarDX/config/project-scratch-def.json username=$SCRATCH_ORG_USERNAME -a ${SCRATCH_ORG_ALIAS} \
    # Override connections in property file with scratch org usernames
    && awk -v username=$SCRATCH_ORG_USERNAME '{print} /{/ && !n {print "  \"connectionOverride\": \n  [\n    { \"connection\": \"Admin\", \"username\": \""username"\" }\n  ],"; n++}' /home/com.provar.plugins.provardx/${PROVARDX_PROPERTY_FILE} > tmp1 && mv tmp1 /home/com.provar.plugins.provardx/${PROVARDX_PROPERTY_FILE} \
    # Deploy metadata to scratch org for admin user
    && cd /home/ProvarDX \
    && sfdx force:mdapi:retrieve -r package -u ${DEV_HUB_USERNAME} -k package.xml \
    && unzip package/unpackaged.zip \
    && sfdx force:mdapi:convert --rootdir unpackaged --outputdir force-app \
    && sfdx force:source:push -u $SCRATCH_ORG_USERNAME

ENV PROVAR_HOME=${REPO_HOME}/Provar_ANT_${PROVAR_VERSION} \
    CACHEPATH=${WORKSPACE}/../.provarCaches 

# ## Set working directory for image
WORKDIR /home/com.provar.plugins.provardx
# Validate ProvarDX property file and compile src in Provar Project
RUN set -ex \
    && cd /home/com.provar.plugins.provardx \
    && sfdx provar:validate -p ${PROVARDX_PROPERTY_FILE} \
    && sfdx provar:compile -p ${PROVARDX_PROPERTY_FILE} 
## Entrypoint script to run Provar tests
RUN echo "#!/bin/sh \n sfdx provar:runtests -p ${PROVARDX_PROPERTY_FILE}" > ./entrypoint.sh
RUN chmod +x ./entrypoint.sh
ENTRYPOINT ["./entrypoint.sh"]
CMD