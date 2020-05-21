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
## ANT Target to run in build file
ARG ANT_TARGET=runtests
## Build file to run tests
ARG BUILD_FILE=build.xml
## Test Plan to target in build file
ARG TEST_PLAN
## Provar secrets password (input either here or as "--build-arg PROVAR_SECRETS_PASSWORD=YOURSECRETSPASSWORD" in build command)
ARG ProvarSecretsPassword
## Salesforce Connection Name
ARG PROVAR_sf_Admin
## Salesforce Connection Password
ARG PROVAR_sf_Admin_password
## This docker build assumes you run as root (-u root)
## Initial stage to build from to get openjdk-8 and ANT installed
FROM frekele/ant:1.10.3-jdk8
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
    PROVAR_sf_Admin_password=${PROVAR_sf_Admin_password} 

RUN set -ex \
    && apt -y update -qq && apt install -y \
    xvfb \ 
    wget \
    npm \
    git \
    && wget -q -O - https://dl-ssl.google.com/linux/linux_signing_key.pub | apt-key add - \
    && echo "deb [arch=amd64] http://dl.google.com/linux/chrome/deb/ stable main" >> /etc/apt/sources.list.d/google.list \
    && apt update -qq \
    && apt install -qq -y google-chrome-stable google-chrome-beta \
    && wget -O /etc/init.d/xvfb https://gist.githubusercontent.com/axilleas/3fc13e0c90ad9f58bee903a41e8a6d48/raw/169a60010635e05eaa902c5f3b4393321f2452f0/xvfb \
    && chmod 0755 /etc/init.d/xvfb \
    && sh -e /etc/init.d/xvfb start \
    && mkdir -p ${REPO_HOME}/Provar_ANT_${PROVAR_DEFAULT_VERSION} \
    && wget -qP ${REPO_HOME} https://download.provartesting.com/${PROVAR_MAJOR_VERSION}/Provar_ANT_${PROVAR_DEFAULT_VERSION}.zip \
    && unzip ${REPO_HOME}/Provar_ANT_${PROVAR_DEFAULT_VERSION}.zip -d ${REPO_HOME}/Provar_ANT_${PROVAR_DEFAULT_VERSION} \
    && rm -rf ${REPO_HOME}/Provar_ANT_${PROVAR_DEFAULT_VERSION}.zip \
    && git clone --single-branch --branch development https://github.com/ProvarTesting/provardx.git /home/ \
    && ant -version \
    && javac -version \
    && npm -version \
    ## Set up project payload && Copy script to PATH
    && mkdir -p ${WORKSPACE}/ANT/Results \
    && mkdir -p ${WORKSPACE}/src \
    && mkdir -p ${WORKSPACE}/lib \
    && mkdir -p ${WORKSPACE}/bin 

RUN set -ex \
    && npm install \
    && npm install -g sfdx-cli \
    && npm install -g yarn \
    && cd /home/com.provar.plugins.provardx | sfdx plugins:link | yarn run prepack \
    && sfdx force:project:create -n /home/ProvarDX 

COPY project-scratch-def.json /home/ProvarDX/config/project-scratch-def.json
COPY package.xml /home/ProvarDX/package.xml
COPY .forceignore /home/ProvarDX/.forceignore

RUN set -ex \
    && sfdx force:auth:jwt:grant --clientid $(consumer_key) --jwtkeyfile "$(serverKey.secureFilePath)" --username $(dev_hub_username) --setdefaultdevhubusername --setalias $(dev_hub_alias)
ENV PROVAR_HOME=${REPO_HOME}/Provar_ANT_${PROVAR_VERSION} \
    CACHEPATH=${WORKSPACE}/../.provarCaches 

## (Optional: Only use for local runs or environments not using a Version Control System) Copy local project files to docker image
## Otherwise files will be pulled from Git/etc. (in that case comment this line) and placed into ${WORKSPACE} where ${WORKSPACE} contains the .testproject file/etc.
COPY com.provar.plugins.provardx ${WORKSPACE}/
COPY ProvarProject ${WORKSPACE}/

## (Optional: Licenses folder must be in same directory as Dockerfile first)
# Copy Licenses folder to container for execution tracking
COPY .licenses/ ${PROVAR_HOME}/.licenses 
COPY .smtp/ ${PROVAR_HOME}/.smtp
## Set working directory for image
WORKDIR ${WORKSPACE}
## Entrypoint script to run Provar tests
RUN echo "#!/bin/sh \n xvfb-run ant -f ANT/$BUILD_FILE" > ./entrypoint.sh
RUN chmod +x ./entrypoint.sh
ENTRYPOINT ["./entrypoint.sh"]
CMD