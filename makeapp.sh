#!/bin/sh

# this script makes an os x app out of an existing ant build folder
# taken command line arguments:
#   1: name of .app
#   2: Info.plist
#
# script is to be invoked from the same directory where the ant build.xml
# resides

app=$1

# clean existing app
if [ -d $app.app ]
then
  rm -rf $app.app
fi

# create directory structure
mkdir makeapp_temp
cd makeapp_temp
mkdir -p $app.app $app.app/Contents/MacOS $app.app/Contents/Resources/Java

# set bundle bit
/usr/bin/SetFile -a B $app.app

# copy Mac OS java application stub
cp -r /System/Library/Frameworks/JavaVM.framework/Versions/Current/Resources/MacOS/ ./$app.app/Contents/MacOS/

# look for Info.plist and Icon file
if [ ! -f ../Info.plist ]
then
  echo "No Info.plist was found, aborting..."
  exit
else
  cp ../Info.plist $app.app/Contents/
fi

if [ ! -f ../*.icns ]
then
  echo "No Icon was found, using default..."
else
  echo "Copying icon file. Remember to make sure it is referenced in your Info.plist..."
  cp ../*.icns $app.app/Contents/Resources/
fi

# copy ant build products
if [ ! -d ../dist ]
then
  echo "No dist directory found, consider ant re-run..."
else
  cp -r ../dist/* $app.app/Contents/Resources/java/
fi

# PkgInfo
touch $app.app/Contents/PkgInfo

# move app to main folder and clean
cd ..
mv makeapp_temp/$app.app ./
rm -rf makeapp_temp


