import java.util.*;
import com.googlecode.lanterna.terminal.Terminal.SGR;
import com.googlecode.lanterna.TerminalFacade;
import com.googlecode.lanterna.input.Key;
import com.googlecode.lanterna.input.Key.Kind;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.Terminal.Color;
import com.googlecode.lanterna.terminal.TerminalSize;
import com.googlecode.lanterna.LanternaException;
import com.googlecode.lanterna.input.CharacterPattern;
import com.googlecode.lanterna.input.InputDecoder;
import com.googlecode.lanterna.input.InputProvider;
import com.googlecode.lanterna.input.Key;
import com.googlecode.lanterna.input.KeyMappingProfile;
import com.googlecode.lanterna.screen.*;

public class Tack{
  private int x, y; //x and y coordinates
  private int direction; //direction of tack movement
  private int steps; //number of steps the tack has taken
  private long sinceTime; //time needed for the next tack movement
  private int delay; //time in between tack movements
  private int hits; //how many lives a tack can take from a balloon with one hit

  /**A constructor of a tack owned by a tackShooter
  *@param int xCord is the x-coordinate
  *@param int yCord is the y-coordinate
  *@param int direx is the direction the tack will move
               0 is up
               1 is right
               2 is down
               3 is left
  *@param int delay is the delay time between the tack's movements
  *@param int hits is how many lives a tack can take from a balloon with one hit
  */
  public Tack(int xCord, int yCord, int direx, int delay, int hits){
    x = xCord;
    y = yCord;
    direction = direx;
    this.delay = delay;
    sinceTime = 0;
    steps = 0;
    this.hits = hits;
  }

  /**A method that will make the tack move according to its direction
  *@param long timer is how long the game has been going on for
  */
  public void move(long timer){
    sinceTime = timer; //time is updated
    if(direction == 0){
      y -= 1;
      steps++;
    }
    if(direction == 1){
      x += 1;
      steps++;
    }
    if(direction == 2){
      y += 1;
      steps++;
    }
    if(direction == 3){
      x -= 1;
      steps++;
    }
    sinceTime += delay; //updates the sinceTime to relect new time the game needs to reach before the next tack movement
    if ((x == 1 || x == 60) && (y >= 3 && y <= 33)) steps = 4; //if the tack reaches the radius of its movement, the number of steps becomes 4 and it will die
    if ((y == 3 || y == 33) && (x >= 1 && x <= 60)) steps = 4;
  }

  /**A method that checks if the tack has hit any balloons based on coordinates
  *@param List<Balloon> balloons
  *@return int earned is the amount of money earned by the user for hitting a balloon
  */
  public int hitTarget(List<Balloon> balloons){
    int earned = 0;
    for (int i = balloons.size()-1; i >= 0; i--){
      Balloon temp = balloons.get(i);
      if (temp.getX() == x && temp.getY() == y){ //if the balloon and tack are at the same coordinates
        temp.setLives(temp.getLives() - hits); //takes lives from the balloon
        if (hits == 2 && temp.getLives() >= 0) earned += 10;
        else if (hits == 2 && temp.getLives() < 0) earned += 5;
        else if (hits == 1) earned += 5; //the user earns $5 for every hit or life the tack takes from the balloon
        if (temp.getLives() <= 0) balloons.remove(i); //if the balloon has no more lives, it is removed
      }
    }
    return earned; //the amount of money earned for taking lives from a balloon
  }

  /**A method that draws the tack onto the screen according to its direction
  *@param Screen s
  */
  public void draw(Screen s){
    if (direction == 0) s.putString(x,y,"^",Terminal.Color.BLUE,Terminal.Color.CYAN);
    if (direction == 1) s.putString(x,y,">",Terminal.Color.BLUE,Terminal.Color.CYAN);
    if (direction == 2) s.putString(x,y,"v",Terminal.Color.BLUE,Terminal.Color.CYAN);
    if (direction == 3) s.putString(x,y,"<",Terminal.Color.BLUE,Terminal.Color.CYAN);
  }

  /**A method that undraws the tack if it is on a road tile
  *@param Screen s
  *@param int xcor of the tack
  *@param int ycor of the tack
  *@param List<Tile> road
  */
  public void undraw(Screen s, int xcor, int ycor, List<Tile> road){
    for (Tile x: road){
      if (xcor == x.getX() && ycor == x.getY()) s.putString(xcor,ycor," ",Terminal.Color.DEFAULT,Terminal.Color.WHITE); //if its on the road, change the background color to white
      else s.putString(xcor,ycor," ",Terminal.Color.DEFAULT,Terminal.Color.GREEN);
    }
  }

  /**A method to get the tack's x-coordinate
  *@return int x
  */
  public int getX(){
    return x;
  }

  /**A method to get the tack's y-coordinate
  *@return int y
  */
  public int getY(){
    return y;
  }

  /**A method to get the nmber of steps the tack has taken
  *@return int steps
  */
  public int getSteps(){
    return steps;
  }

  /**A method to get the next time the game needs to reach before the tack can move again
  *@return long sinceTime
  */
  public long getSince(){
    return sinceTime;
  }

  /**A method to get a tack's direction
  *@return int 0 is up
               1 is right
               2 is down
               3 is left
  */
  public int getDirection(){
    return direction;
  }

  /**A method to set the steps a tack has taken
  *@param int num
  */
  public void setSteps(int num){
    steps = num;
  }

}
