#!/bin/sh
cd /home
# Validate ProvarDX property file with plugin
echo "Validate ProvarDX property file with plugin"
sfdx provar:validate -p /home/$PROVARDX_PROPERTY_FILE 
# Compile ProvarDX property file with plugin
echo "Compile Provar project property file with plugin"
sfdx provar:compile -p /home/$PROVARDX_PROPERTY_FILE 
# Run Provar tests on Scratch Org with ProvarDX plugin
echo "Starting Provar tests on Scratch Org"
echo y | xvfb-run sfdx provar:runtests -p /home/$PROVARDX_PROPERTY_FILE
echo "---------------Test Run Complete---------------"