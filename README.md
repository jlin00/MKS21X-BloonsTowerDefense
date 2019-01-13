# Bloons Tower Defense
This project aims to recreate a game known as Bloons Tower Defense. The program will be interactive and act similarly to the original game. The user will be able to choose a map to play the game in. Each map will have a road for the balloons to pass through and surrounding grass to place the towers. Balloons will enter from one end of the road and continue traveling until they either reach the end of the road or are popped by surrounding towers. Each balloon that reaches the end of the road will decrease the lives of the user. When the user has no more lives and has passed all the levels, the game ends. The user will steadily earn income as time passes and can use it to purchase towers that pop the balloons.

## Instructions
After cloning the repo, the user must compile a few files in order for the game to operate. These files, namely Tile.java, Balloon.java, and Game.java, must be compiled with the lanterna jar file. To do this, use the terminal command "javac -cp lanterna:. <file_name.java>" For example, "javac -cp lanterna.jar: Tile.java" Afterward, use the terminal command "java -cp lanterna.jar:. <file_name>" such as "java -cp lanterna.jar:. Game". Only the Game class needs to be compiled in the latter fashion since it is the only file with a main. The default map of the game is Map 1. If you would like to use map 2, run "java -cp lanterna.jar:. Game 2" instead. Be careful, because if any other argument is passed besides 1 and 2, a map will not be generated.

Once Game.java is compiled, a terminal screen will appear, displaying the Bloons Tower Defense Game. To start the game, press B once, which will load the map of the game. Pressing A will pause the game. Money is earned as time goes on— $75 every 10 seconds. When the game is paused, the timer stops as well, so money will not be gained during this period. The currency can be used to buy towers. The game screen will display a chart of each tower type and their respective keys. To place a tower, use the arrow keys to move the cursor along the map to the tile that you want to place your tower on. Then press the key corresponding to the type of tower you want to place. Remember that the tile must be green and not a road tile.

The game is played by placing towers, which all have a radius, to attack balloons that will spawn onto the screen once the game starts. The balloons will move down the road tiles and each time a balloon reaches the end of the road, a life is lost. Users have a limited amount of lives. After they are used up, the user loses that level. Successfully popping all the balloons means the user can advance to the next level.

## Development Log
<table>
  <th>Log Date</th>
  <th>Description</th>
  <tr>
    <td>1/3/19</td>
    <td>Today, we created the new repository. We learned about lanterna.jar and will be spending some time trying to
    figure out how to use it. The formatting for the README.md was also finalized. </td>
  </tr>
<tr>
    <td>1/4/19</td>
    <td>Today, we worked on creating the Tile and Balloon classes, and added some methods. We decided to add a few more
    variables to our Balloon class and also to combine the Map and User classes into one big Game class.
    </td>
</tr>
<tr>
    <td>1/5/19</td>
    <td>Today, we experimented with lanterna.jar by creating different types of modes (such as starting mode and game mode).
    We did not code anything definitive because we are still trying to understand how to use lanterna.jar. We anticipate
    this task to be one of the more challenging ones as we move forward.
    </td>
</tr>
<tr>
    <td>1/6/19</td>
    <td>Today, we experimented with some graphics of the game. We read in coordinates from a map file, and tried to color
    in the terminal accordingly. This task is still a work in progress. We will continue working on it tomorrow, and will
    also continue working on the Tile class.
    </td>
</tr>
<tr>
    <td>1/7/19</td>
    <td>Today, we continued to experiment with the terminal graphics of the game. We started to format the graphics and achieved having two modes, one screen for starting up the game and second screen for the map of the game. The basic Tile class was finished with some additions, like the Balloon List, and we implemented it to the Game terminal. We also finished the Tower abstract class and started working on the three different towers, TackShooter, RoadSpike, and IceTower, that extend the abstract class.
    </td>
</tr>
<tr>
    <td>1/8/19</td>
    <td>Today, we continued writing methods for the Tile and Balloon classes. Hopefully, we will soon be able to test out their behaviors in the terminal. We are also working on features like drawing the map and setting up a timer system for the game.
    </td>
</tr>
<tr>
    <td>1/9/19</td>
    <td>Today, we continued working on developing the graphics of the game. We attempted to implement the Balloon class in the terminal, with
    limited success. We also merged the two branches together and will modify methods over the next few days as we test them out.  
    </td>
</tr>
<tr>
    <td>1/10/19</td>
    <td>Today, we worked on implementing the balloon class and having it show up on the terminal. We still haven't figured out the timer system, so hopefully we
    fix that problem sometime in the near future. We will also start working on implementing the tower class in order to prepare for demos.   
    </td>
</tr>
<tr>
    <td>1/11/19</td>
    <td>Today, we continued to on implementing the balloon objects onto the terminal class. We had many challenges with this, such as the balloon getting stuck after some time and some flickering issues, but we at least managed to have one balloon object moving along the road. To address these issues, we went to our teacher to see if he could help us fix anything, and we received advice on how to improve the game and solve the problems. Besides the terminal graphics, we also revised the attack methods in each of the different towers.
    </td>
</tr>
<tr>
    <td>1/12/19</td>
    <td>Today, we tried implementing the changes that our teacher had advised us to implement. Though the graphics of the terminal improved significantly due to these changes, we continued to have problems with the flickering balloons and the balloon getting stuck after a while. We had also changed the workings of the Balloon and Tile class so now they have to be compiled with the lanterna.jar in order for the game to work. Meanwhile, we also updated instructions for the game.
    </td>
  <td>1/13/19</td>
    <td>
    </td>
</table>
