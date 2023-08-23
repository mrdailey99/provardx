#!/bin/bash

## Clone Dreamhouse App
echo "--- Clone Dreamhouse App Scratch Org project ---"
git clone https://github.com/dreamhouseapp/dreamhouse-lwc
cd dreamhouse-lwc
## Deploy Dreamhouse to Scratch Org
echo "--- Deploy Dreamhouse to Scratch Org ---"
sf project deploy start --target-org $1
## Assign dreamhouse permission set to the default user
echo "--- Assign dreamhouse permission set to the default user ---"
sf org assign permset -n dreamhouse --target-org $1
## Import sample data into Scratch Org
echo "--- Import sample data into Scratch Org ---"
sf data import tree -p data/sample-data-plan.json --target-org $1