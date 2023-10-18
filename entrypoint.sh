#!/bin/bash

# Start XVFB
Xvfb $DISPLAY -screen 0 1024x768x16 &

# Run your tests with Provar CLI
xvfb-run ant -f ANT/build.xml -v