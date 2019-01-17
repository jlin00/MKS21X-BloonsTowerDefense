//API : http://mabe02.github.io/lanterna/apidocs/2.1/
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

public class Spike extends Tower{

  /**A Tower constructor
  *@param int xCord is the x position of the tower, also its row in the array
  *@param int yCord is the y position of the tower, also its column in the array
  *@param int money is the cost
  */
  public Spike(int xCord, int yCord, int money){
    x = xCord;
    y = yCord;
    cost = money;
  }

  /**A method that takes one life from the balloon target on its tile
  public void attack(List<Balloon> balls){
    for(int i = 0; i < balls.size(); i++){
      Balloon temp = balls.get(i);
      if(temp.getIsAlive() && temp.getInit()){
        if(temp.getX() == this.getX() && temp.getY() == this.getY()){
          temp.setLives(temp.getLives() - 1);
          if(temp.getLives() == 0){
            temp.makeDead();
          }
        }
      }
    }
  }
  */
}
