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

public class SpikeTower extends Tower{

  private int delay;
  private long sinceShot;
  private int radius;

  /**A Tower constructor
  *@param int xCord is the x position of the tower, also its row in the array
  *@param int yCord is the y position of the tower, also its column in the array
  *@param int money is the cost
  *@param int rad is the radius
  */
  public SpikeTower(int xCord, int yCord, int money, int delay, int rad){
    x = xCord;
    y = yCord;
    cost = money;
    sinceShot = 0;
    this.delay = delay;
    radius = rad;
  }

  public long getSince(){
    return sinceShot;
  }

  public void draw(Screen s){
    s.putString(x,y,"S",Terminal.Color.WHITE,Terminal.Color.BLUE);
  }

  public void spawnSpikes(List<Spike> spikes, long timer, int delay){
    sinceShot = timer;
    for (int i = 0; i < 4; i++){
      spikes.add(new Spike(x,y,i,delay));
    }
    sinceShot += this.delay;
  }

  public boolean inRadius(List<Tile> roads){
    for (int i = roads.size() - 1; i >= 0; i--){
      Tile temp = roads.get(i);
      if ((temp.getX() >= x - radius && temp.getX() <= x + radius && temp.getY() == y) || (temp.getY() >= y - radius && temp.getY() <= y + radius && temp.getX() == x)){
        return true;
      }
    }
    return false;
  }

}
