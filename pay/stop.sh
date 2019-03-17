#!/bin/sh
ps -ef | grep pay | grep -v grep | awk '{print $2}' | xargs kill -9
