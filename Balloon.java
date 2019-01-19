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
  private int lives, delay; //tnumber of lives the balloon has; the time between balloon movements
  private boolean isAlive; //status of the balloon
  private long sinceMoved; //time needed for the next balloon movement
  private int xcor, ycor; //x and y coordinates
  private int atTile; //the number of the road tile that the balloon is currently on

  /**A Balloon constructor
  *the default number of lives is 1
  */
  public Balloon(){
    lives = 1;
    isAlive = true;
  }

  /**A second Balloon constructor
  *@param int number of lives can be determined by the user
  *the balloon is "placed" at the first road tile
  */
  public Balloon(int numLives){
    lives = numLives;
    isAlive = true;
    atTile = 0;
  }

  /**A third and most specific Balloon constructor
  *@param int number of lives can be determined by the user
  *@param int delay is the interval of time in seconds that must be passed before the balloon can move to the next road tile
  *@param int xcor is its x-coordinate on the screen
  *@param int ycor is its y-coordinate on the screen
  *the balloon is "placed" at the first road tile
  */
  public Balloon(int numLives, int delay, int xcor, int ycor){
    lives = numLives;
    isAlive = true;
    this.delay = delay;
    this.xcor = xcor;
    this.ycor = ycor;
    atTile = 0; //the balloon is "placed" at the first road tile
    sinceMoved = 0; //this variable keeps the next time at which a balloon can move again
  }

  /**A method to get the number of lives a balloon has
  *@return int lives
  */
  public int getLives(){
    return lives;
  }

  /**A method to get the delay of a balloon, in terms of movement across the road
  *@return int delay
  */
  public int getDelay(){
    return delay;
  }

  /**A method to check if a balloon is alive
  *@return boolean isAlive
  */
  public boolean getIsAlive(){
    return isAlive;
  }

  /**A method to get x-coordinate of balloon
  *@return int xcor
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

  /**A method to get the road tile that the balloon is on
  *the road tiles are numbered based on their order in the array list of road tiles
  *@return int atTile
  */
  public int getTile(){
    return atTile;
  }

  /**A method to get the next time the game needs to reach before the balloon can move again
  *@return long sinceMoved
  */
  public long getSince(){
    return sinceMoved;
  }

  /**A method to change the number of lives a balloon has
  *@param int numLives
  */
  public void setLives(int numLives){
    lives = numLives;
  }

  /**A method to change the delay time of a balloon
  *@param int num
  */
  public void setDelay(int num){
    delay = num;
  }

  /**A method to make a balloon 'dead' by changing the isAlive boolean to false
  */
  public void makeDead(){
    isAlive = false;
  }

  /**A method to set the road tile of a balloon
  *@param int num (of the road tile's position in the list of road tiles)
  */
  public void setTile(int num){
    atTile = num;
  }

  /**A method to move the balloon in the terminal to the next road tile using the coordinates of that tile
  *@param Tile t is the next road tile
  *@param long timer is how long the game has been going on
  */
  public void move(Tile t, long timer){
    sinceMoved = timer;
    xcor = t.getX();
    ycor = t.getY();
    atTile++;
    sinceMoved += delay; //the delay is added to reflect the new time the game needs to reach before the balloon can move again
  }

  /**A method that draws the balloon onto the terminal
  **this is only used when a terminal is used for displaying the game; if a screen is used, this method is not needed
  *@param Terminal t
  */
  public void draw(Terminal t){
    t.moveCursor(xcor, ycor);
    t.applyBackgroundColor(Terminal.Color.WHITE);
    t.applyForegroundColor(Terminal.Color.RED);
    t.putCharacter('Ǫ');
    t.applyBackgroundColor(Terminal.Color.DEFAULT);
    t.applyForegroundColor(Terminal.Color.DEFAULT);
  }

  /**A method that draws the balloon onto the screen
  *@param Screen s
  *the color of the balloon changes depending on the amount of lives it has
  */
  public void draw(Screen s){
    if (lives == 1) s.putString(xcor,ycor,"\u29ed",Terminal.Color.RED,Terminal.Color.WHITE);
    if (lives == 2) s.putString(xcor,ycor,"\u29ed",Terminal.Color.YELLOW,Terminal.Color.WHITE);
    if (lives == 3) s.putString(xcor,ycor,"\u29ed",Terminal.Color.GREEN,Terminal.Color.WHITE);
    if (lives == 4) s.putString(xcor,ycor,"\u29ed",Terminal.Color.CYAN,Terminal.Color.WHITE);
    if (lives == 5) s.putString(xcor,ycor,"\u29ed",Terminal.Color.BLUE,Terminal.Color.WHITE);
    if (lives == 6) s.putString(xcor,ycor,"\u29ed",Terminal.Color.MAGENTA,Terminal.Color.WHITE);
    if (lives == 7) s.putString(xcor,ycor,"\u29ed",Terminal.Color.BLACK,Terminal.Color.WHITE);
  }

}
