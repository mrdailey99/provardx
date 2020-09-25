#!/bin/sh
cd /root/ProvarDX/ProvarDX
scratch_org_alias=ProvarDX
# Authorize dev hub to generate scratch orgs
echo "Authorize Dev Hub in container"
sfdx force:auth:jwt:grant --clientid $CONSUMER_KEY --jwtkeyfile /root/ProvarDX/server.key --username $DEV_HUB_USERNAME --setdefaultdevhubusername --setalias $DEV_HUB_ALIAS --instanceurl $INSTANCE_URL
sfdx force:config:set defaultdevhubusername=$DEV_HUB_USERNAME --global 
echo "---------Dev Hub Successfully Authorized-----------"
# Create scratch org with scratch_org_alias
echo "Create Scratch Org"
cat config/project-scratch-def.json 
sfdx force:org:create -f /root/ProvarDX/ProvarDX/config/project-scratch-def.json --setalias $scratch_org_alias --durationdays $SCRATCH_ORG_DURATION --setdefaultusername --json --loglevel fatal 
sfdx force:config:set defaultusername=$scratch_org_alias --global 
sfdx force:org:display -u $scratch_org_alias 
echo "---------Scratch Org Successfully Created-----------"
# Override connections in property file with scratch org usernames
echo "Override connections in ProvarDX property file"
/root/ProvarDX/create_connection_overrides.sh $CONNECTION_NAME $scratch_org_alias /root/ProvarDX/$PROVARDX_PROPERTY_FILE 
echo "---------Connections Successfully Overridden-----------"
# Insert secrets password into property file (if present)
echo "Insert secrets password into ProvarDX property file"
/root/ProvarDX/insert_secrets_password.sh $ProvarSecretsPassword /root/ProvarDX/$PROVARDX_PROPERTY_FILE 
# Deploy metadata to scratch org for admin user
echo "Retrieve metadata from Dev Hub and push to Scratch Org"
sfdx force:mdapi:retrieve -r package -u $DEV_HUB_USERNAME -k /root/ProvarDX/ProvarDX/package.xml 
unzip /root/ProvarDX/ProvarDX/package/unpackaged.zip 
sfdx force:mdapi:convert --root/ProvarDXdir /root/ProvarDX/ProvarDX/unpackaged --outputdir /root/ProvarDX/ProvarDX/force-app 
sfdx force:source:push -u ProvarDX
echo "---------Metadata Successfully Pushed to Scratch Org-----------"

echo "---------SCRIPT COMPLETE-----------"
echo "-------------------------------------"
echo "---------READY FOR TESTING-----------"