#!/bin/sh
mkdir bin

javac -d bin -cp lib/BreezySwing.jar src/*/*.java

rm -f *.jar
ant -buildfile game.xml
ant -buildfile server.xml
ant -buildfile leveleditor.xml

zip -r tanks.zip Game.jar Server.jar LevelEditor.jar assets src bin lib 
