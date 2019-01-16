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
  private int x, y;
  private int direction;
  private int steps;
  private long sinceTime;
  private int delay;//attackTime is time between each attack

  /**A constructor of a tack owned by a tackShooter
  *@param int xCord is the x coordinate
  *@param int yCord is the y coordinate
  *@param int direx is the direction of the tack
               0 is up
               1 is right
               2 is down
               3 is left
  */
  public Tack(int xCord, int yCord, int direx, int delay){
    x = xCord;
    y = yCord;
    direction = direx;
    this.delay = delay;
    sinceTime = 0;
    steps = 0;
  }

  /**A method that will make the tack move according to its direction and attack balloons as it goes
  */

  public void move(long timer){
    sinceTime = timer;
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
    sinceTime += delay;
  }

  public void hitTarget(List<Balloon> balloons){
    for (int i = balloons.size()-1; i >= 0; i--){
      Balloon temp = balloons.get(i);
      if (temp.getX() == x && temp.getY() == y){
        temp.setLives(temp.getLives() - 1);
        System.out.println("HIT!!!");
        if (temp.getLives() == 0) balloons.remove(i);
      }
    }
  }

  public void draw(Screen s){
    if (direction == 0) s.putString(x,y,"^",Terminal.Color.BLUE,Terminal.Color.CYAN);
    if (direction == 1) s.putString(x,y,">",Terminal.Color.BLUE,Terminal.Color.CYAN);
    if (direction == 2) s.putString(x,y,"v",Terminal.Color.BLUE,Terminal.Color.CYAN);
    if (direction == 3) s.putString(x,y,"<",Terminal.Color.BLUE,Terminal.Color.CYAN);
  }

  public void undraw(Screen s, int xcor, int ycor, List<Tile> road){
    for (Tile x: road){
      if (xcor == x.getX() && ycor == x.getY()) s.putString(xcor,ycor," ",Terminal.Color.DEFAULT,Terminal.Color.WHITE);
      else s.putString(xcor,ycor," ",Terminal.Color.DEFAULT,Terminal.Color.GREEN);
    }
  }
  /**A method to get the tack's x coordinate
  */
  public int getX(){
    return x;
  }

  /**A method to get the tack's y coordinate
  */
  public int getY(){
    return y;
  }

  /**A method to get the tack's steps
  */
  public int getSteps(){
    return steps;
  }

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

}
