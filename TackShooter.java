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

  private List<Tack> tacks = new ArrayList<Tack>();

  /**A Tower constructor
  *@param int xCord is the x position of the tower, also its row in the array
  *@param int yCord is the y position of the tower, also its column in the array
  *@param int money is the cost
  *@param int rad is the radius
  */
  public TackShooter(int xCord, int yCord, int money, int rad){
    x = xCord;
    y = yCord;
    cost = money;
    radius = rad;
  }

  public void draw(Screen s){
    s.putString(x,y,"T",Terminal.Color.WHITE,Terminal.Color.BLUE);
  }

  /**A method that creates four tacks and calls for them to move and attack
  *once the tack reaches outside of the radius, it is removed from the list of tacks
  */

  /*
  public void attack(){
    tacks.add(new Tack(this.getX(), this.getY(), 1));
    tacks.add(new Tack(this.getX(), this.getY(), 2));
    tacks.add(new Tack(this.getX(), this.getY(), 3));
    tacks.add(new Tack(this.getX(), this.getY(), 4));
    for(int i = 0; i < tacks.size(); i++){
      Tack temp = tacks.get(i);
      if(temp.getSteps() > this.getRadii()){
        tacks.remove(i);
      }
      if(temp.getSteps() < this.getRadii()){
        temp.move();
      }
    }
  }
  */
}
