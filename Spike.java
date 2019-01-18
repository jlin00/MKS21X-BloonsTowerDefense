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

  private int lives;

  /**A Tower constructor
  *@param int xCord is the x position of the tower, also its row in the array
  *@param int yCord is the y position of the tower, also its column in the array
  *@param int money is the cost
  */
  public Spike(int xCord, int yCord, int money, int lives){
    x = xCord;
    y = yCord;
    cost = money;
    this.lives = lives;
  }

  public int getLives(){
    return lives;
  }

  public void loseLife(){
    lives--;
  }

  public void hitTarget(List<Balloon> balloons){
    for (int i = balloons.size()-1; i >= 0; i--){
      Balloon temp = balloons.get(i);
      if (temp.getX() == x && temp.getY() == y){
        temp.setLives(temp.getLives() - 1);
        lives--;
        if (temp.getLives() == 0) balloons.remove(i);
      }
    }
  }

  public void draw(Screen s){
    s.putString(x,y,"*",Terminal.Color.DEFAULT,Terminal.Color.WHITE);
  }


}
