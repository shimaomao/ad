#!/bin/sh
ps -ef | grep advertise | grep -v grep | awk '{print $2}' | xargs kill -9
