#!/bin/sh
cd /home
scratch_org_alias=ProvarDX
echo "Delete Scratch Org from Dev Hub"
sfdx force:org:delete -u $scratch_org_alias --noprompt
echo "-----------------SCRATCH ORG DELETED-----------------"