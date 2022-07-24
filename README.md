# csc413-tankgame


| Student Information |                |
|:-------------------:|----------------|
|  Student Name       |   Timmy Tram    |
|  Student Email      |   ttram2@mail.sfsu.edu   |

## src Folder Purpose 
src folder is to be used to store source code only.

## resources Folder Purpose 
resources folder is to be used to store the resources for your project only. This includes images, sounds, map text files, etc.

`The src and resources folders can be deleted if you want a different file structure`

## jar Folder Purpose 
The jar folder is to be used to store the built jar of your term-project.

`NO SOURCE CODE SHOULD BE IN THIS FOLDER. DOING SO WILL CAUSE POINTS TO BE DEDUCTED`

`THIS FOLDER CAN NOT BE DELETED OR MOVED`

# Required Information when Submitting Tank Game

## Version of Java Used: Java version 16.0.2

## IDE used: IntelliJ IDEA Ultimate Edition 2022.1.2

## Steps to Import project into IDE:

## Steps to Build your Project:
 
## Steps to run your Project:

## Controls to play your Game:

|               | Player 1 | Player 2 |
|---------------|----------|----------|
|  Forward      |     W     |    UP ARROW      |
|  Backward     |      S    |     DOWN ARROW     |
|  Rotate left  |       A   |     LEFT ARROW     |
|  Rotate Right |       D   |     RIGHT ARROW     |
|  Shoot        |        SPACEBAR  |    ENTER      |

<!-- you may add more controls if you need to. -->

## Map Building

A Template of the Map is provided in the resources/maps folder named "template.csv" and is a 40x30 csv.


#### Note: make sure save as CSV (Comma delimited) (*.csv) in MS Excel and NOT CSV UTF-8
1. PLAYER1 <-- Creates a playable Trainer (REQUIRED FOR ALL MAPS AND MUST HAVE ONLY 1!)
2. PLAYER2 <-- Creates a playable Pokemon (REQUIRED FOR ALL MAPS AND MUST HAVE ONLY 1!)
3. BORDER <-- Creates an unbreakable wall that should be placed at the edge of the world
4. EMPTY <-- No gameObject created
5. UWALL <-- Creates an unbreakable wall
6. BWALL <-- Creates a breakable wall
7. HEAL <-- Creates a Blissey Power Up
8. BARRAGE <-- Creates a Blastoise Power Up
9. SPEED <-- Creates a Deoxys Speed Power Up

### Adding the Map to the Game
1. Place your map into the resources/maps folder
2. Go to src/tankgame/constants and open ResourceConstants.java
3. Scroll to the comment where Map constants are listed
4. Create another constant following the structure as the other map constants
5. Go to src/tankgame and open ResourceHandler.java
6. Go to the function initMaps() and add another line of code to add your map
7. Then simply rebuild the game artifact as listed above