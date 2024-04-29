# Software Engineering Project 2

## Blackbox+ 

![alt text](/Gameplay3.png)

This project was built in the context of the UCD module COMP20050 Software Engineering.
It implements the board game Back Box+ by Eric Solomon from the experimenter's perspective, with added GUI, in Java.
For more information on Black Box+ game and rules: https://tesera.ru/images/items/841139/BB+.pdf
### Launching
- Download blackboxgame-1.0-SNAPSHOT.jar
- run "java -jar blackboxgame-1.0-SNAPSHOT.jar"
For troubleshooting, the JDK used was openjdk-17.


### Playing: 
The **main menu** gives you access to the options menu and new game button.
The **options menu** allows you to change the game's board settings (number of atoms to find, board size, sound, etc.)
From Options Menu:

Gameplay:
- "Set Rays" allows you to shoot rays from the board's edges and find out where they exit the board
- "Set Atoms" (selected by default) allows you to place/remove atoms on the board
- End the game when you think you guessed all atoms based on the rays' supposed trajectories
Get as fewer points as possible. Points are attributed as follows:
- 1 point per ray shot
- 5 points per misplaced atom

### Features per Sprint:
##### Sprint 1: Board and Atoms
- rendering hexagon board (+entry numbers and arrows)
- rendering atoms
- placing atoms
- randomly placing atoms
- atom properties (=state)
- rendering circles of influence

##### Sprint 2: Rays 
- rendering ray markers
- rendering rays
- placing ray markers
- announcing ray results
- calculating ray paths
- points

##### Sprint 3: End Game and Menu Screens 
- end game button
- display score
- display true board
- menu screen
- menu buttons (play, replay, options)

##### Sprint 4: Options/Polish 
- options menu 
- atom number option 
- sound option 
- board size option 
- best score