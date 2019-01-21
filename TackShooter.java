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

public class TackShooter extends Tower{

  private int delay; //the time between each round of tack shooting
  private long sinceShot; //the time needed for the next round of tack shooting
  private int upgrade; //tkeeps track of upgrade number
  private int hits; //how many lives a tack can take from a balloon with one hit

  /**A TackShooter constructor
  *@param int xCord is the TackShooter's x-coordinate on the screen
  *@param int yCord is the TackShooter's y-coordinate on the screen
  *@param int money is the cost
  *@param int delay is the time between each round of tack shooting
  *@param int rad is the radius
  *@param int hits is how many lives a tack can take from a balloon with one hit
  */
  public TackShooter(int xCord, int yCord, int money, int delay, int rad, int hits){
    x = xCord;
    y = yCord;
    cost = money;
    sinceShot = 0;
    this.delay = delay;
    radius = rad;
    upgrade = 0;
    this.hits = hits;
  }

  /**A method to get the next time the game needs to reach before the TackShooter can shoot again
  *@return long sinceShot
  */
  public long getSince(){
    return sinceShot;
  }

  /**A method to get the number of times the TackShooter has been upgraded
  *@return int upgrade
  */
  public int getUpgrade(){
    return upgrade + 1;
  }

  /**A method that gets the number of lives a tack takes from a balloon with one hit
  *@return int hits
  */
  public int getHits(){
    return hits;
  }

  /**A method to get the delay of the TackShooter, in terms of when the TackShooter can shoot out tacks again
  *@return int delay
  */
  public int getDelay(){
    return delay;
  }

  /**A method that draws the TackShooter onto the screen using the letter T to represent it
  *@param Screen s
  */
  public void draw(Screen s){
    if (upgrade == 0) s.putString(x,y,"t",Terminal.Color.WHITE,Terminal.Color.BLUE);
    if (upgrade == 1) s.putString(x,y,"T",Terminal.Color.WHITE,Terminal.Color.BLUE);
  }

  /**A method to create tack objects
  *@param List<Tack> tacks
  *@param long timer is how long the game has been going on for
  *@param int delay is the time in between tack movements
  */
  public void spawnTacks(List<Tack> tacks, long timer, int delay){
    sinceShot = timer; //time is updated
    for (int i = 0; i < 4; i++){ //creates four tacks with different directions
      tacks.add(new Tack(x,y,i,delay,hits));
    }
    sinceShot += this.delay; //the delay is added to reflect the new time the game needs to reach for the TackShooter to shoot again
  }

  /**A method that checks if a balloon is within the radius of the TackShooter based on coordinates before it shoots
  *@param List<Balloon> balloons
  *@return boolean
  */
  public boolean inRadius(List<Balloon> balloons){
    for (int i = balloons.size() - 1; i >= 0; i--){
      Balloon temp = balloons.get(i);
      if ((temp.getX() >= x - radius && temp.getX() <= x + radius && temp.getY() == y) || (temp.getY() >= y - radius && temp.getY() <= y + radius && temp.getX() == x)){
        return true;
      }
    }
    return false;
  }

  /**A method that upgrades the TackShooter
  *tacks shot by the upgraded can take more from a balloon in a single hit
  *the delay time between tack shooting is decreased by 300 milliseconds
  */
  public void upgrade(){
    hits++;
    delay-=300;
    upgrade++;
  }

  /**A method that checks if the TackShooter can be upgraded
  *TackShooters can only be upgraded once
  *@return boolean
  */
  public boolean canUpgrade(){
    return (upgrade == 0);
  }

}
