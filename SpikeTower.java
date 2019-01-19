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

  private int delay; //time between spike placements
  private long sinceShot; //time needed for the next spike placement
  private int radius; //range in which the SpikeTower can place spikes
  private int upgrade; //keeps track of upgrade number
  private int lives;

  /**A SpikeTower constructor
  *@param int xCord is the SpikeTower's x-coordinate on the screen
  *@param int yCord is the SpikeTower's y-coordinate on the screen
  *@param int money is the cost
  *@param int delay is the time needed to pass before the Spike Tower can place another spike
  *@param int rad is the radius
  */
  public SpikeTower(int xCord, int yCord, int money, int delay, int rad, int lives){
    x = xCord;
    y = yCord;
    cost = money;
    sinceShot = 0;
    this.delay = delay;
    radius = rad;
    upgrade = 0;
    this.lives = lives;
  }

  /**A method to get the next time the game needs to reach before the SpikeTower can place a spike again
  *@return long sinceShot
  */
  public long getSince(){
    return sinceShot;
  }

  /**A method that draws the SpikeTower onto the screen using the letter S to represent it
  *@param Screen s
  */
  public void draw(Screen s){
    s.putString(x,y,"S",Terminal.Color.WHITE,Terminal.Color.RED);
  }

  /**A method to create spike objects to be placed onto a road tile on the screen
  *@param List<Spike> spikes
  *@param List<Tile> road
  *@param long timer is how long the game has been going on for
  *@param int delay is the time in between spike placement
  *@param int money is the cost of the spikes
  *@param int lives is the amount of lives the spikes will have
  */
  public void spawnSpikes(List<Spike> spikes, List<Tile> road, long timer, int delay, int money){
    sinceShot = timer;
    Tile toSpawn = pickRoad(road);
    spikes.add(new Spike(toSpawn.getX(),toSpawn.getY(),money,lives));
    sinceShot += this.delay; //the delay is added to reflect the new time the game needs to reach for the SpikeTower to place another spike
  }

  /**A method that checks if a road tile is within the radius of the SpikeTower based on coordinates
  *@param Tile road
  *@return boolean
  */
  public boolean inRadius(Tile road){
    if (road.getX() >= x - radius && road.getX() <= x + radius && road.getY() >= y - radius && road.getY() <= y + radius) return true;
    return false;
  }

  /**A method that puts all the road tiles within the radius of the SpikeTower into a list and randomly chooses a road tile from that List
  *@param List<Tile> road
  *@return Tile (a randomly chosen road tile within radius)
  */
  public Tile pickRoad(List<Tile> road){
    List<Tile> eligible_tiles = new ArrayList<Tile>();
    for (Tile x: road){ //if the road tile is within radius...
      if (inRadius(x)) eligible_tiles.add(x);
    }
    int max = eligible_tiles.size() - 1;
    int min = 0;
    int range = max + 1;
    int rand = (int)(Math.random() * range) + min; //get a random road tile from the list eligible_tiles
    return eligible_tiles.get(rand);
  }

  public void upgrade(){
    lives++;
    delay-=1500;
    upgrade++;
  }

  public boolean canUpgrade(){
    return (upgrade < 2);
  }

}
