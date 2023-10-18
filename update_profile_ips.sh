#!/bin/sh
echo "arg 1"
echo $1
echo "arg 2"
echo $2
sed -i "s|<description>Azure Agent</description>|$1|" $2