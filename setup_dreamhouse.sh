#!/bin/bash

## Clone Dreamhouse App
echo "--- Clone Dreamhouse App Scratch Org project ---"
git clone https://github.com/dreamhouseapp/dreamhouse-lwc
cd dreamhouse-lwc
## Deploy Dreamhouse to Scratch Org
echo "--- Deploy Dreamhouse to Scratch Org ---"
sfdx force:source:push -u $1
## Assign dreamhouse permission set to the default user
echo "--- Assign dreamhouse permission set to the default user ---"
sfdx force:user:permset:assign -n dreamhouse -u $1
## Import sample data into Scratch Org
echo "--- Import sample data into Scratch Org ---"
sfdx force:data:tree:import -p data/sample-data-plan.json -u $1