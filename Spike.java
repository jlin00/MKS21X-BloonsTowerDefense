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

  private int lives; //the number of lives a spike has

  /**A Tower constructor
  *@param int xCord is the x position of the tower, also its row in the array
  *@param int yCord is the y position of the tower, also its column in the array
  *@param int money is the cost
  *@param int lives is the number of lives the spike has
  *lives are the number of balloons a spike can pop before it "dies" and disappears
  */
  public Spike(int xCord, int yCord, int money, int lives){
    x = xCord;
    y = yCord;
    cost = money;
    this.lives = lives;
  }

  /**A method to get the number of lives the spike currently has
  *@return int Lives
  */
  public int getLives(){
    return lives;
  }

  /**A method that takes a life away from the spike
  */
  public void loseLife(){
    lives--;
  }

  /**A method that checks if a spike still has lives left, as in the spike can still hit balloons
  *@return boolean
  */
  public boolean hasLife(){
    return (lives > 0);
  }

  /**A method that checks if a balloon is on the same road tile as the spike and takes away a life from the balloon
  *the method reads through the list of balloons and checks their coordinates
  *@param List<Balloon> balloons
  *@return int earned is the amount of money earned by the user for hitting a balloon
  */
  public int hitTarget(List<Balloon> balloons){
    int earned = 0;
    for (int i = balloons.size()-1; i >= 0; i--){
      Balloon temp = balloons.get(i);
      if (temp.getX() == x && temp.getY() == y){ //if a balloon is on the same road tile
        if (hasLife()) temp.setLives(temp.getLives() - 1); //if the spike can still hit balloons
        loseLife(); //the spike loses a life
        earned += 5;
        if (temp.getLives() == 0) balloons.remove(i); //if the balloon has no more lives, it is removed
      }
    }
    return earned; //the amount of money earned for taking a life from a balloon
  }

  /**A method that draws the spike onto the screen
  *@param Screen s
  */
  public void draw(Screen s){
    s.putString(x,y,"*",Terminal.Color.BLACK,Terminal.Color.WHITE);
  }


}
