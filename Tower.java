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
import java.util.*;

public abstract class Tower{
  int x, y;
  int cost;
  int radius;
  Balloon target;

  /**A Tower constructor
  *@param int xCord is the x position of the tower, also its row in the array
  *@param int yCord is the y position of the tower, also its column in the array
  *@param int money is the cost
  *@param int rad is the radius
  */
/*  abstract Tower(int xCord, int yCord, int money, int rad){
    x = xCord;
    y = yCord;
    cost = money;
    radius = rad;
    findVicinity();
  }
*/

  /**A method to get the x value, or the row of the tower in the array
  *@return int
  */
  public int getX(){
    return x;
  }

  /**A method to get the y value, or the column of the tower in the array
  *@return int
  */
  public int getY(){
    return y;
  }

  /**A method to get the cost of the tower
  *@return int
  */
  public int getCost(){
    return cost;
  }

  /**A method to get the radius of the tower
  *@return int
  */
  public int getRadii(){
    return radius;
  }

  /**A method to be written by the individual towers
  *attacks are different for each type of tower
  */

  /**A method to find a balloon target for the tower
  *the balloon target is the nearest balloon on a road tile within the radius of the tower
  *@return Balloon
  */
  /*
  public Balloon findTarget(List<Tile> roads){
    double distance = (radius * radius) + 1;
    Tile temp;
    Tile targetTile = new Tile(0,0);
    for(int i = 0; i < roads.size(); i++){
      temp = roads.get(i);
      if(temp.getHasBalloon()){
        double distTemp = Math.pow(this.getX() - temp.getX(), 2) + Math.pow(this.getY() - temp.getY(), 2);
        if(distTemp < distance){
          distance = distTemp;
          targetTile = temp;
        }
      }
    } List<Balloon> possibleTargets = targetTile.getBalloons();
      target = possibleTargets.get(Math.abs(100 % possibleTargets.size()));
      return target;
  }
  */

  public void draw(Terminal t){
    t.moveCursor(x, y);
    t.applyBackgroundColor(Terminal.Color.BLACK);
    t.putCharacter(' ');
    t.applyBackgroundColor(Terminal.Color.DEFAULT);
    t.applyForegroundColor(Terminal.Color.DEFAULT);
  }
}
