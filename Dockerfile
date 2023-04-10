## Pre-Build:
## (Optional Execution License): 
## Make a copy of your local .licenses/https://urldefense.proofpoint.com/v2/url?u=http-3A__MYLICENSE.properties&d=DwIGaQ&c=08AGY6txKsvMOP6lYkHQpPMRA1U6kqhAwGa8-0QCg3M&r=Q9GUOfR_3_1JNxdPSL3yYw_CsHsqMZOB42nLIZUx9tc&m=2xb4-WVJ_0QHR5RYqNvziBVYBklSZThR2uxjfZVW4pyHoxMfVed781zFlxbzsFGk&s=9YMaokgPL8H5WIeF0lGZjf78IhZAIx0ZFF182EOS5mM&e=  file
## Replace the existing key with your execution-only license key 
## Create a .licenses folder in your test project directory and place the execution-only properties file there
## (Optional SMTP Configuration): 
## Ensure you have setup an email account for sending emails through Provar. This is done via the email configuration tab 
## on the ANT build screen. 
## Copy the contents of your $USER_HOME/Provar/.smtp folder to your test project directory.
## (Run script): Make sure https://urldefense.proofpoint.com/v2/url?u=http-3A__runProvarTests.sh&d=DwIGaQ&c=08AGY6txKsvMOP6lYkHQpPMRA1U6kqhAwGa8-0QCg3M&r=Q9GUOfR_3_1JNxdPSL3yYw_CsHsqMZOB42nLIZUx9tc&m=2xb4-WVJ_0QHR5RYqNvziBVYBklSZThR2uxjfZVW4pyHoxMfVed781zFlxbzsFGk&s=AhNNVMIA-cQOyocruvXS9tUCy654BIKD1s7EHuBld4s&e=  is present in the test project directory.
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
ARG BUILD_FILE=DockerBS_build.xml
## Test Plan to target in build file
ARG TEST_PLAN
## Provar secrets password (input either here or as "--build-arg PROVAR_SECRETS_PASSWORD=YOURSECRETSPASSWORD" in build command)
ARG ProvarSecretsPassword
## Salesforce Connection Name
ARG PROVAR_sf_Admin
## Salesforce Connection Password
ARG PROVAR_sf_Admin_password
# Proxy Details  --- If setting Proxy details via args to env variables than arg values will be passed from Build Args as ARGS scope is only till image build
#ARG com_provar_proxy_port=3128
#ARG com_provar_proxy_host=https://urldefense.proofpoint.com/v2/url?u=http-3A__100.22.17.86&d=DwIGaQ&c=08AGY6txKsvMOP6lYkHQpPMRA1U6kqhAwGa8-0QCg3M&r=Q9GUOfR_3_1JNxdPSL3yYw_CsHsqMZOB42nLIZUx9tc&m=2xb4-WVJ_0QHR5RYqNvziBVYBklSZThR2uxjfZVW4pyHoxMfVed781zFlxbzsFGk&s=dm15TQuqtteLQzUjzizkQzquk5Mf_OHAR6d4HKkWrhk&e= 
#ARG com_provar_proxy_type=HTTP
#ARG com_provar_proxy_active=true
#ARG com_provar_proxy_password=Provar01
#ARG com_provar_proxy_user=provar
## This docker build assumes you run as root (-u root)
## Initial stage to use base image with ANT and Oracle JDK 8
FROM ubuntu:latest
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
    gnupg \
    unzip \
    && apt-get install -y openjdk-11-jdk \
    && apt-get install -y ant
RUN wget -q -O - https://urldefense.proofpoint.com/v2/url?u=https-3A__dl.google.com_linux_linux-5Fsigning-5Fkey.pub&d=DwIGaQ&c=08AGY6txKsvMOP6lYkHQpPMRA1U6kqhAwGa8-0QCg3M&r=Q9GUOfR_3_1JNxdPSL3yYw_CsHsqMZOB42nLIZUx9tc&m=2xb4-WVJ_0QHR5RYqNvziBVYBklSZThR2uxjfZVW4pyHoxMfVed781zFlxbzsFGk&s=elwCqV-gAHFWu6H8tglN02muWIDFH10D99blhQglVto&e=  | apt-key add - \
    && echo "deb [arch=amd64] https://urldefense.proofpoint.com/v2/url?u=http-3A__dl.google.com_linux_chrome_deb_&d=DwIGaQ&c=08AGY6txKsvMOP6lYkHQpPMRA1U6kqhAwGa8-0QCg3M&r=Q9GUOfR_3_1JNxdPSL3yYw_CsHsqMZOB42nLIZUx9tc&m=2xb4-WVJ_0QHR5RYqNvziBVYBklSZThR2uxjfZVW4pyHoxMfVed781zFlxbzsFGk&s=tZRhFxMpUWW4B-NLXJ2FQLfkXW9ldE8kAVBQo6XEdOo&e=  stable main" | tee /etc/apt/sources.list.d/chrome.list \
    && apt update -qq \
    && apt-cache search chrome \
    && apt-get install google-chrome-stable -y \
    && wget -O /etc/init.d/xvfb https://urldefense.proofpoint.com/v2/url?u=https-3A__gist.githubusercontent.com_axilleas_3fc13e0c90ad9f58bee903a41e8a6d48_raw_169a60010635e05eaa902c5f3b4393321f2452f0_xvfb&d=DwIGaQ&c=08AGY6txKsvMOP6lYkHQpPMRA1U6kqhAwGa8-0QCg3M&r=Q9GUOfR_3_1JNxdPSL3yYw_CsHsqMZOB42nLIZUx9tc&m=2xb4-WVJ_0QHR5RYqNvziBVYBklSZThR2uxjfZVW4pyHoxMfVed781zFlxbzsFGk&s=GN3t03nNeQgVxe7-WOXRdKoXrec54Lp7yiCutDNAUXI&e=  \
    && chmod 0755 /etc/init.d/xvfb \
    && sh -e /etc/init.d/xvfb start \
    && mkdir -p ${REPO_HOME}/Provar_ANT_${PROVAR_DEFAULT_VERSION} \
    && wget -qP ${REPO_HOME} https://urldefense.proofpoint.com/v2/url?u=https-3A__download.provartesting.com_-24-257BPROVAR-5FMAJOR-5FVERSION-257D_Provar-5FANT-5F-24-257BPROVAR-5FDEFAULT-5FVERSION-257D.zip&d=DwIGaQ&c=08AGY6txKsvMOP6lYkHQpPMRA1U6kqhAwGa8-0QCg3M&r=Q9GUOfR_3_1JNxdPSL3yYw_CsHsqMZOB42nLIZUx9tc&m=2xb4-WVJ_0QHR5RYqNvziBVYBklSZThR2uxjfZVW4pyHoxMfVed781zFlxbzsFGk&s=doNSDf5X1IR9VGv4XtZGCgWZqLGox0aDKIHKzieaTMU&e=  \
    && unzip ${REPO_HOME}/Provar_ANT_${PROVAR_DEFAULT_VERSION}.zip -d ${REPO_HOME}/Provar_ANT_${PROVAR_DEFAULT_VERSION} \
    && rm -rf ${REPO_HOME}/Provar_ANT_${PROVAR_DEFAULT_VERSION}.zip \
    && ant -version \
    && javac -version \
    ## Set up project payload && Copy script to PATH
    && mkdir -p ${WORKSPACE}/ANT/Results \
    && mkdir -p ${WORKSPACE}/src \
    && mkdir -p ${WORKSPACE}/lib \
    && mkdir -p ${WORKSPACE}/bin \
    ## Disable Setuid/setgid binaries
    && find / -xdev -perm /6000 -type f -exec chmod a-s {} \; || true

ENV PROVAR_HOME=${REPO_HOME}/Provar_ANT_${PROVAR_VERSION} \
    CACHEPATH=${WORKSPACE}/../.provarCaches 

### Proxy Env Variables
#ENV com_provar_proxy_port=3128 \
	#com_provar_proxy_host=https://urldefense.proofpoint.com/v2/url?u=http-3A__100.22.17.86&d=DwIGaQ&c=08AGY6txKsvMOP6lYkHQpPMRA1U6kqhAwGa8-0QCg3M&r=Q9GUOfR_3_1JNxdPSL3yYw_CsHsqMZOB42nLIZUx9tc&m=2xb4-WVJ_0QHR5RYqNvziBVYBklSZThR2uxjfZVW4pyHoxMfVed781zFlxbzsFGk&s=dm15TQuqtteLQzUjzizkQzquk5Mf_OHAR6d4HKkWrhk&e=  \
	#com_provar_proxy_active=true \
	#com_provar_proxy_type=HTTP \
	#com_provar_proxy_user=provar \
	#com_provar_proxy_password=Provar01

#### Setting up proxy in Docker Image\Container	
#ENV http_proxy=https://urldefense.proofpoint.com/v2/url?u=http-3A__100.22.17.86-3A3128&d=DwIGaQ&c=08AGY6txKsvMOP6lYkHQpPMRA1U6kqhAwGa8-0QCg3M&r=Q9GUOfR_3_1JNxdPSL3yYw_CsHsqMZOB42nLIZUx9tc&m=2xb4-WVJ_0QHR5RYqNvziBVYBklSZThR2uxjfZVW4pyHoxMfVed781zFlxbzsFGk&s=Bw3Gpb-DFN90gYUqDGPVoZ_cn-RuF_8QM5tsVWSyz54&e=  \
    #https_proxy=https://urldefense.proofpoint.com/v2/url?u=http-3A__100.22.17.86-3A3128&d=DwIGaQ&c=08AGY6txKsvMOP6lYkHQpPMRA1U6kqhAwGa8-0QCg3M&r=Q9GUOfR_3_1JNxdPSL3yYw_CsHsqMZOB42nLIZUx9tc&m=2xb4-WVJ_0QHR5RYqNvziBVYBklSZThR2uxjfZVW4pyHoxMfVed781zFlxbzsFGk&s=Bw3Gpb-DFN90gYUqDGPVoZ_cn-RuF_8QM5tsVWSyz54&e= 
	
#### ANT opts for getting the proxy logs if set in PRovar
#ENV ANT_OPTS="-Dcom.provar.proxy.debug.trace=true"

## (Optional: Only use for local runs or environments not using a Version Control System) Copy local project files to docker image
## Otherwise files will be pulled from Git/etc. (in that case comment this line) and placed into ${WORKSPACE} where ${WORKSPACE} contains the .testproject file/etc.
COPY . ${WORKSPACE}/
## (Optional: Licenses folder must be in same directory as Dockerfile first)
# Copy Licenses folder to container for execution tracking
# Copy SMTP folder for Email reporting in Docker image (must have SMTP set up in image)
# COPY .licenses/ ${PROVAR_HOME}/.licenses 
# COPY .smtp/ ${PROVAR_HOME}/.smtp
## Set working directory for image
WORKDIR ${WORKSPACE}
## Entrypoint script to run Provar tests
RUN echo "#!/bin/sh \n xvfb-run ant -f ANT/$BUILD_FILE" > ./https://urldefense.proofpoint.com/v2/url?u=http-3A__entrypoint.sh&d=DwIGaQ&c=08AGY6txKsvMOP6lYkHQpPMRA1U6kqhAwGa8-0QCg3M&r=Q9GUOfR_3_1JNxdPSL3yYw_CsHsqMZOB42nLIZUx9tc&m=2xb4-WVJ_0QHR5RYqNvziBVYBklSZThR2uxjfZVW4pyHoxMfVed781zFlxbzsFGk&s=YjJ_xogC1n1yfnuILLrLsdqtNQ1SPRJMZBhu9PnSTyU&e= 
RUN chmod +x ./https://urldefense.proofpoint.com/v2/url?u=http-3A__entrypoint.sh&d=DwIGaQ&c=08AGY6txKsvMOP6lYkHQpPMRA1U6kqhAwGa8-0QCg3M&r=Q9GUOfR_3_1JNxdPSL3yYw_CsHsqMZOB42nLIZUx9tc&m=2xb4-WVJ_0QHR5RYqNvziBVYBklSZThR2uxjfZVW4pyHoxMfVed781zFlxbzsFGk&s=YjJ_xogC1n1yfnuILLrLsdqtNQ1SPRJMZBhu9PnSTyU&e= 
ENTRYPOINT ["./https://urldefense.proofpoint.com/v2/url?u=http-3A__entrypoint.sh&d=DwIGaQ&c=08AGY6txKsvMOP6lYkHQpPMRA1U6kqhAwGa8-0QCg3M&r=Q9GUOfR_3_1JNxdPSL3yYw_CsHsqMZOB42nLIZUx9tc&m=2xb4-WVJ_0QHR5RYqNvziBVYBklSZThR2uxjfZVW4pyHoxMfVed781zFlxbzsFGk&s=YjJ_xogC1n1yfnuILLrLsdqtNQ1SPRJMZBhu9PnSTyU&e= "]
CMD