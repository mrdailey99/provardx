#!/bin/sh
cd /home/ProvarProject/tests

git ls-files -o --exclude-standard --full-name > /home/new_tests.txt
sed -i 's/ProvarProject\/tests\///' /home/new_tests.txt
$new_tests=cat /home/new_tests.txt
sed -i "s|NEWTESTS|$new_tests|" /home/provardx-properties-dev.json

cd /home
# Validate ProvarDX property file with plugin
echo "Validate ProvarDX property file with plugin"
sfdx provar:validate -p /home/provardx-properties-dev.json
# Compile ProvarDX property file with plugin
echo "Compile Provar project property file with plugin"
sfdx provar:compile -p /home/provardx-properties-dev.json
# Run Provar tests on Scratch Org with ProvarDX plugin
echo "Starting Provar tests on Scratch Org"
echo y | xvfb-run sfdx provar:runtests -p /home/provardx-properties-dev.json
echo "---------------Test Run Complete---------------"