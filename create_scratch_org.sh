#!/bin/sh

# Authorize dev hub to generate scratch orgs
echo "Authorize Dev Hub in container"
sfdx force:auth:jwt:grant --clientid $CONSUMER_KEY --jwtkeyfile /home/server.key --username $DEV_HUB_USERNAME --setdefaultdevhubusername --setalias $DEV_HUB_ALIAS --instanceurl $INSTANCE_URL
sfdx force:config:set defaultdevhubusername=$DEV_HUB_USERNAME --global 
echo "---------Dev Hub Successfully Authorized-----------"
# Create scratch org with SCRATCH_ORG_USERNAME set in project JSON
echo "Create Scratch Org"
sed -i "s|SCRATCH_ORG_USERNAME|$SCRATCH_ORG_USERNAME|" config/project-scratch-def.json 
cat config/project-scratch-def.json 
sfdx force:org:create -f config/project-scratch-def.json --setalias $SCRATCH_ORG_ALIAS --durationdays $SCRATCH_ORG_DURATION --setdefaultusername --json --loglevel fatal 
sfdx force:config:set defaultusername=$SCRATCH_ORG_USERNAME --global 
sfdx force:org:display -u $SCRATCH_ORG_ALIAS 
echo "---------Scratch Org Successfully Created-----------"
# Override connections in property file with scratch org usernames
echo "Override connections in ProvarDX property file"
chmod +x /home/create_connection_overrides.sh 
/home/create_connection_overrides.sh $CONNECTION_NAME $SCRATCH_ORG_USERNAME /home/$PROVARDX_PROPERTY_FILE 
echo "---------Connections Successfully Overridden-----------"
# Insert secrets password into property file (if present)
echo "Insert secrets password into ProvarDX property file"
chmod +x /home/insert_secrets_password.sh 
/home/insert_secrets_password.sh $ProvarSecretsPassword /home/$PROVARDX_PROPERTY_FILE 
# Deploy metadata to scratch org for admin user
echo "Retrieve metadata from Dev Hub and push to Scratch Org"
sfdx force:mdapi:retrieve -r package -u $DEV_HUB_USERNAME -k package.xml 
unzip package/unpackaged.zip 
sfdx force:mdapi:convert --rootdir unpackaged --outputdir force-app 
sfdx force:source:push --targetusername $SCRATCH_ORG_USERNAME
echo "---------Metadata Successfully Pushed to Scratch Org-----------"

echo "---------SCRIPT COMPLETE-----------"
echo "-------------------------------------"
echo "---------READY FOR TESTING-----------"