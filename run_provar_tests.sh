#!/bin/sh
cd /home/ProvarDX
# Validate ProvarDX property file with plugin
echo "Validate ProvarDX property file with plugin"
sfdx provar:validate -p /home/ProvarDX/$PROVARDX_PROPERTY_FILE 
# Compile ProvarDX property file with plugin
echo "Compile Provar project property file with plugin"
sfdx provar:compile -p /home/ProvarDX/$PROVARDX_PROPERTY_FILE 
# Run Provar tests on Scratch Org with ProvarDX plugin
echo "Starting Provar tests on Scratch Org"
xvfb-run sfdx provar:runtests -p /home/ProvarDX/$PROVARDX_PROPERTY_FILE
echo "---------------Test Run Complete---------------"