#!/bin/sh
if [ "$#" -ne 3 ]; then
    echo "Usage (requires 3 parameters): create_connection_overrides.sh CONNECTION_NAME SCRATCH_ORG_USERNAME PROPERTY_FILE_JSON"
    exit 1  
fi
sed -i "s|CONNECTION_NAME|$1|" $3
sed -i "s|SCRATCH_ORG_USERNAME|$2|" $3