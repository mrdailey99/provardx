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
## Environment Level (Provar Test Environment)
ARG ENV
## Provar secrets password (input either here or as "--build-arg PROVAR_SECRETS_PASSWORD=YOURSECRETSPASSWORD" in build command)
ARG ProvarSecretsPassword
## The username sfdx will use to authenticate to the dev hub for Salesforce and create scratch orgs
ARG DEV_HUB_USERNAME
## The alias used for the dev hub for sfdx
ARG DEV_HUB_ALIAS
## The unique username for the Scratch Org to be created with
ARG SCRATCH_ORG_USERNAME
## Consumer Key for dev hub authentication
ARG CONSUMER_KEY
## The alias for the scratch org (doesn't need to be unique)
ARG SCRATCH_ORG_ALIAS=ProvarDX
## Dev hub instance URL
ARG INSTANCE_URL="https://login.salesforce.com"
ARG PROVARDX_PROPERTY_FILE=provardx-properties-docker.json
## Connection name for ProvarDX to override
ARG CONNECTION_NAME=Admin
## Duration in days for Scratch Org to persist
ARG SCRATCH_ORG_DURATION=7

## This docker build assumes you run as root (-u root)
## Initial stage to build from to get openjdk-8 and ANT installed
FROM ubuntu:16.04
LABEL maintainer="Provar Testing <support@provartesting.com>"
LABEL version="1.0"
ARG PROVAR_DEFAULT_VERSION
ARG PROVAR_MAJOR_VERSION
ARG PROJECT_NAME
ARG ENV
ARG ProvarSecretsPassword
ARG DEV_HUB_USERNAME
ARG DEV_HUB_ALIAS
ARG SCRATCH_ORG_USERNAME
ARG CONSUMER_KEY
ARG SCRATCH_ORG_ALIAS
ARG INSTANCE_URL
ARG PROVARDX_PROPERTY_FILE
ARG CONNECTION_NAME
ARG SCRATCH_ORG_DURATION

# The location to save the Provar binaries to (from downloads page)
ENV REPO_HOME=/srv/Provar \
    PROVAR_VERSION=${PROVAR_DEFAULT_VERSION} \
    ## Set the WORKSPACE for execution
    WORKSPACE=/home/${PROJECT_NAME} \
    JAVA_ARGS=-verbose:class \
    ENVIRONMENT=${ENV} \
    ProvarSecretsPassword=${ProvarSecretsPassword} \
    DEV_HUB_USERNAME=${DEV_HUB_USERNAME} \
    DEV_HUB_ALIAS=${DEV_HUB_ALIAS} \
    SCRATCH_ORG_USERNAME=${SCRATCH_ORG_USERNAME} \
    SCRATCH_ORG_ALIAS=${SCRATCH_ORG_ALIAS} \
    INSTANCE_URL=${INSTANCE_URL} \
    PROVARDX_PROPERTY_FILE=${PROVARDX_PROPERTY_FILE} \
    CONNECTION_NAME=${CONNECTION_NAME} \
    SCRATCH_ORG_DURATION=${SCRATCH_ORG_DURATION}

ENV PROVAR_HOME=${REPO_HOME}/Provar_ANT_${PROVAR_VERSION} \
    CACHEPATH=${WORKSPACE}/../.provarCaches 

COPY . /home

RUN set -ex \
    && apt -y update -qq && apt install -y \
    xvfb \ 
    wget \
    curl \
    git \
    sudo \
    build-essential \
    openjdk-8-jdk \
    unzip \
    # Install NodeJS and NPM
    && curl -sL https://deb.nodesource.com/setup_12.x | sudo -E bash - \
    && apt install -y nodejs 
    
RUN set -ex \
    # Install latest chrome release    
    && wget -q -O - https://dl-ssl.google.com/linux/linux_signing_key.pub | apt-key add - \
    && echo "deb [arch=amd64] http://dl.google.com/linux/chrome/deb/ stable main" >> /etc/apt/sources.list.d/google.list \
    && apt update -qq  && apt install -qq -y google-chrome-stable google-chrome-beta \
    && wget -O /etc/init.d/xvfb https://gist.githubusercontent.com/axilleas/3fc13e0c90ad9f58bee903a41e8a6d48/raw/169a60010635e05eaa902c5f3b4393321f2452f0/xvfb \
    && chmod 0755 /etc/init.d/xvfb \
    && sh -e /etc/init.d/xvfb start \
    # Install latest Provar libraries
    && mkdir -p ${REPO_HOME}/Provar_ANT_${PROVAR_DEFAULT_VERSION} \
    && wget -qP ${REPO_HOME} https://download.provartesting.com/${PROVAR_MAJOR_VERSION}/Provar_ANT_${PROVAR_DEFAULT_VERSION}.zip \
    && unzip ${REPO_HOME}/Provar_ANT_${PROVAR_DEFAULT_VERSION}.zip -d ${REPO_HOME}/Provar_ANT_${PROVAR_DEFAULT_VERSION} \
    && rm -rf ${REPO_HOME}/Provar_ANT_${PROVAR_DEFAULT_VERSION}.zip \
    # Setup ProvarDX property file 
    && sed -i "s|PROVAR_HOME|$PROVAR_HOME|" /home/$PROVARDX_PROPERTY_FILE \
    && sed -i "s|ENVIRONMENT|$ENVIRONMENT|" /home/$PROVARDX_PROPERTY_FILE \
    && javac -version \
    && node --version \
    && npm --version \
    # && google-chrome --version \
    ## Set up project payload && Copy script to PATH
    && mkdir -p ${WORKSPACE}/src \
    && mkdir -p ${WORKSPACE}/lib \
    && mkdir -p ${WORKSPACE}/bin \
    && chmod +x /home/create_scratch_org.sh \
    && chmod +x /home/delete_scratch_org.sh \
    && chmod +x /home/run_provar_tests.sh \
    && chmod +x /home/insert_secrets_password.sh \
    && chmod +x /home/create_connection_overrides.sh
    # Remove additional packages 
    # && apt remove -y git \
    # curl \
    # wget 

## Install sfdx-cli and setup ProvarDX plugin 
RUN set -ex \
    && npm install sfdx-cli --global \
    ## noprompt option not available, so piping echoed 'y' to approve plugin
    && echo y | sfdx plugins:install @provartesting/provardx \
    && sfdx plugins:update \
    && cp -r $WORKSPACE/provardx $PROVAR_HOME \
    # verify SFDX CLI version
    && sfdx --version 

# Setup scratch org project to deploy metadata
RUN if [ $(sfdx plugins) == *@provartesting/provardx* ]; then cd /home/ \
    && sfdx force:project:create -n ProvarDX \
    && cp /home/project-scratch-def.json /home/ProvarDX/config/project-scratch-def.json \
    && cp /home/package.xml /home/ProvarDX/package.xml \
    && cp /home/.forceignore /home/ProvarDX/.forceignore ; \
    else echo "ProvarDX plugin not successfully installed" ; fi

# Set working directory for image
WORKDIR /home/ProvarDX
CMD []