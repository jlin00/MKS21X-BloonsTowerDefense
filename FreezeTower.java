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

public class FreezeTower extends Tower{

  /**A Tower constructor
  *@param int xCord is the x position of the tower, also its row in the array
  *@param int yCord is the y position of the tower, also its column in the array
  *@param int money is the cost
  *@param int rad is the radius
  */
  public FreezeTower(int xCord, int yCord, int money, int rad){
    x = xCord;
    y = yCord;
    cost = money;
    radius = rad;
  }

  public void attack(List<Balloon> ball, long timer){
    for(int i = 0; i < ball.size(); i++){
      Balloon temp = ball.get(i);
      double distance = Math.pow(this.getX() - temp.getX(), 2) + Math.pow(this.getY() - temp.getY(), 2);
      if(temp.getIsAlive() && temp.getInit()){
        if(distance <= (radius * radius)){
          temp.setDelay(5000);
        }
      }
    }
  }

  /*placing an iceTower on the terminal, to be placed in the GameScreen class
  if(toggle >= 1 && key.getCharacter() == 'i'){
    if(isPlaceable(cursorX, cursorY, road, IceTowers) && (money - IceTowerPrice >= 0){
      IceTowers.add(new iceTower(cursorX, cursorY, IceTowerPrice, IceTowerRad));
      money -= IceTowerPrice;
      cursorX++;
  }
  */

  //s.putString(tba,tba,"IceTower: key I, Price "+IceTowerPrice+", Radius "+IceTowerRad+"",Terminal.Color.DEFAULT,Terminal.Color.DEFAULT);
}
