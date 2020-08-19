#!/bin/sh

echo "Delete Scratch Org from Dev Hub"
sfdx force:org:delete -u $SCRATCH_ORG_ALIAS --noprompt
echo "-----------------SCRATCH ORG DELETED-----------------"