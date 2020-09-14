#!/bin/bash

scratch_org_username=provarwebinar21@provartesting.com

# Install ProvarDX Plugin
echo "--- Install ProvarDX Plugin ---"
echo y | sfdx plugins:install @provartesting/provardx

# Authenticate Dev Hub
echo "--- Authenticate Dev Hub ---"
sfdx force:auth:jwt:grant --clientid $CONSUMER_KEY --jwtkeyfile $HOME/JWT/server.key --username michael.dailey@brave-otter-7uycux.com --setdefaultdevhubusername --setalias TrailheadHub

# Create Scratch Org Project
sfdx force:project:create -n ProvarDX
cp project-scratch-def.json ProvarDX/config/project-scratch-def.json
cp package.xml ProvarDX/package.xml
cp .forceignore ProvarDX/.forceignore

# Clone Dreamhouse App
echo "--- Clone Dreamhouse App Scratch Org project ---"
git clone https://github.com/dreamhouseapp/dreamhouse-lwc
cd dreamhouse-lwc

# Create Scratch Org
echo "--- Create Scratch Org ---"
sfdx force:org:create -s -f config/project-scratch-def.json -a dreamhouse username=$scratch_org_username

# Deploy Dreamhouse to Scratch Org
echo "--- Deploy Dreamhouse to Scratch Org ---"
sfdx force:source:push

# Deploy Admin Profile to Scratch Org
echo "--- Deploy Admin Profile to Scratch Org ---"
cd ../ProvarDX
sfdx force:mdapi:retrieve -r package -u TrailheadHub -k package.xml
unzip ./package/unpackaged.zip
sfdx force:mdapi:convert --rootdir unpackaged --outputdir force-app
sfdx force:source:push -u $scratch_org_username -f
cd ../dreamhouse-lwc

# Assign dreamhouse permission set to the default user
echo "--- Assign dreamhouse permission set to the default user ---"
sfdx force:user:permset:assign -n dreamhouse

# Import sample data into Scratch Org
echo "--- Import sample data into Scratch Org ---"
sfdx force:data:tree:import -p data/sample-data-plan.json

# Override connection Details
../create_connection_overrides.sh Admin $scratch_org_username ../ProvarProject/provardx-properties.json
# ../insert_secrets_password.sh $ProvarSecretsPassword ../ProvarProject/provardx-properties.json