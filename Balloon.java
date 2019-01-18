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

public class Balloon{
  private int lives, delay;
  private boolean isAlive;
  private long sinceMoved;
  private int xcor, ycor;
  private int atTile;

  /**A Balloon constructor
  *@param int each balloon has a given ID number to differentiate it easily
  *the default number of lives is 1
  */
  public Balloon(){
    lives = 1;
    isAlive = true;
  }

  /**A second Balloon constructor
  *@param int each balloon has a given ID nummber
  *@param int number of lives can be determined by the user
  */
  public Balloon(int numLives){
    lives = numLives;
    isAlive = true;
    atTile = 0;
  }

  public Balloon(int numLives, int delay, int xcor, int ycor){//most specific constructor
    lives = numLives;
    isAlive = true;
    this.delay = delay;
    this.xcor = xcor;
    this.ycor = ycor;
    atTile = 0;
    sinceMoved = 0;
  }

  /**A method to get the number of lives a balloon has
  *@return int lives
  */
  public int getLives(){
    return lives;
  }

  /**A method to get the speed of a balloon, in terms of movement across the road
  *@return int delay
  */
  public int getDelay(){
    return delay;
  }

  /**A method to check if a balloon is alive
  *@return int xcor
  */
  public boolean getIsAlive(){
    return isAlive;
  }

  /**A method to get x-coordinate of balloon
  *@return boolean isAlive
  */
  public int getX(){
    return xcor;
  }

  /**A method to get y-coordinate of balloon
  *@return int ycor
  */
  public int getY(){
    return ycor;
  }

  /**A method to get tile of balloon
  *@return int ycor
  */
  public int getTile(){
    return atTile;
  }

  public long getSince(){
    return sinceMoved;
  }

  /**A method to change the number of lives a balloon has
  *@param int numLives
  */
  public void setLives(int numLives){
    lives = numLives;
  }

  /**A method to change the speed of a balloon
  *@param int speed
  */
  public void setDelay(int num){
    delay = num;
  }

  /**A method to make a balloon 'dead' by changing the isAlive boolean to false
  */
  public void makeDead(){
    isAlive = false;
  }

  /**A method to set the tile of a balloon
  */
  public void setTile(int num){
    atTile = num;
  }

  /**A method to move the balloon in the terminal to a new coordinate
  */
  public void move(Tile t, long timer){
    sinceMoved = timer;
    xcor = t.getX();
    ycor = t.getY();
    atTile++;
    sinceMoved += delay;
  }

  public void draw(Terminal t){
    t.moveCursor(xcor, ycor);
    t.applyBackgroundColor(Terminal.Color.WHITE);
    t.applyForegroundColor(Terminal.Color.RED);
    t.putCharacter('Ǫ');
    t.applyBackgroundColor(Terminal.Color.DEFAULT);
    t.applyForegroundColor(Terminal.Color.DEFAULT);
  }

  public void draw(Screen s){
    s.putString(xcor,ycor,"Ǫ",Terminal.Color.RED,Terminal.Color.WHITE);
  }

}
