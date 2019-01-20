#!/bin/bash
resize -s 50 110

javac -cp lanterna.jar:. Balloon.java
javac -cp lanterna.jar:. Tile.java
javac -cp lanterna.jar:. SpikeTower.java
javac -cp lanterna.jar:. Spike.java
javac -cp lanterna.jar:. TackShooter.java
javac -cp lanterna.jar:. Tower.java
javac -cp lanterna.jar:. Tack.java
javac -cp lanterna.jar:. GameScreen.java
java -cp lanterna.jar:. GameScreen
