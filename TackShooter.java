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
  private int upgrade;
  private int hits;

  /**A TackShooter constructor
  *@param int xCord is the TackShooter's x-coordinate on the screen
  *@param int yCord is the TackShooter's y-coordinate on the screen
  *@param int money is the cost
  *@param int delay is the time between each round of tack shooting
  *@param int rad is the radius
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

  public int getUpgrade(){
    return upgrade + 1;
  }

  public int getHits(){
    return hits;
  }

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
    sinceShot = timer;
    for (int i = 0; i < 4; i++){ //creates four tacks with different directions
      tacks.add(new Tack(x,y,i,delay,hits));
    }
    sinceShot += this.delay; //the delay is added to reflect the new time the game needs to reach for the TackSHooter to shoot again
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

  public void upgrade(){
    hits++;
    delay-=300;
    upgrade++;
  }

  public boolean canUpgrade(){
    return (upgrade == 0);
  }

}
