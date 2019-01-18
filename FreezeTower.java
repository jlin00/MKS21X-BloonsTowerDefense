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
import java.util.*;

public class FreezeTower extends Tower{

  private int delay;
  private long sinceShot;
  private long freezeTime;

  /**A Tower constructor
  *@param int xCord is the x position of the tower, also its row in the array
  *@param int yCord is the y position of the tower, also its column in the array
  *@param int money is the cost
  *@param int rad is the radius
  */
  public FreezeTower(int xCord, int yCord, int money, int rad, int delay){
    x = xCord;
    y = yCord;
    cost = money;
    radius = rad;
    this.delay = delay;
    sinceShot = 0;
  }

  public long getSince(){
    return sinceShot;
  }

  public void draw(Screen s){
    s.putString(x,y,"F",Terminal.Color.WHITE,Terminal.Color.MAGENTA);
  }

}
