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
import com.googlecode.lanterna.screen.*;

public class Tile{
  private int x, y;
  private boolean isRoad, isStart, isEnd, hasTower, hasBalloon;
  private List<Balloon> onTile = new ArrayList<Balloon>();

  /**A tile constructor
  *@param integers xCord and yCord are the x and y coordinates on the tile
  *they represent the tile's place on the 2D Tile array of the map
  */
  public Tile(int xCord, int yCord){
    x = xCord;
    y = yCord;
  }

  /**A method to get the x coordinate of a tile
  *it is also the row of the array map that the tile is in
  */
  public int getX(){
    return x;
  }

  /**A method to get the y coordinate of a tile
  *it is also the column of the array map that the tile is in
  */
  public int getY(){
    return y;
  }

  /**A method that returns whether the tile is a road tile (true) or a grass tile (false)
  *@return a boolean
  */
  public boolean getIsRoad(){
    return isRoad;
  }

  /**A method that returns whether a tile is the starting tile of the road (true)
  *@return a boolean
  */
  public boolean getIsStart(){
    return isStart;
  }

  /**A method that returns whether a tile is the ending tile of the road (true)
  *@return a boolean
  */
  public boolean getIsEnd(){
    return isEnd;
  }

  /**A method that returns whether a tile has a tower on it or not (true)
  *@return a boolean
  */
  public boolean getHasTower(){
    return hasTower;
  }

  /**A method that returns whether a tile has a balloon on it or not (true)
  *@return a boolean
  */
  public boolean getHasBalloon(){
    return hasBalloon;
  }

  /**A method that sets a tile as a road tile
  */
  public void makeRoad(){
    isRoad = true;
  }

  /**A method that sets a tile as a grass tile
  */
  public void makeGrass(){
    isRoad = false;
  }

  /**A method that makes a road tile the starting tile where balloons will spawn
  **Precondition: the tile must be a road tile in order to be the starting tile
                  the tile must not be the ending road tile
  *@return boolean depending on if setting the tile as the start was successful
  */
  public boolean makeStart(){
    if(isRoad && !isEnd){
      isStart = true;
      return true;
    }else{
      return false;
    }
  }

  /**A method that makes a road tile the ending tile where balloons will exit
  **Precondition: the tile must be a road tile in order to be the ending tile
                  the tile must not be the starting road tile
  *@return boolean depending on if setting the tile as the end was successful
  */
  public boolean makeEnd(){
    if(isRoad && !isStart){
      isEnd = true;
      return true;
    }else{
      return false;
    }
  }

  /**A method to get the list of balloons on a tile
  *@return List<Balloon>
  */
  public List<Balloon> getBalloons(){
    return onTile;
  }

  public void draw(Terminal t){
    t.moveCursor(x, y);
    t.applyBackgroundColor(Terminal.Color.WHITE);
    t.putCharacter(' ');
    t.applyBackgroundColor(Terminal.Color.DEFAULT);
    t.applyForegroundColor(Terminal.Color.DEFAULT);
  }

  public void draw(Screen s){
    s.putString(x,y," ",Terminal.Color.DEFAULT,Terminal.Color.WHITE);
  }
}
