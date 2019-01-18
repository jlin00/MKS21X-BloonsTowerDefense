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
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.*;

public class GameScreen{

  public static void drawBorder(int r, int c, int length, Screen s){ //draws a border for the game, terminal must be at least 110 x 38
    for (int i = 0; i < length; i++){
      s.putString(r,c+i," ",Terminal.Color.DEFAULT,Terminal.Color.BLACK);
    }

    for (int i = 0; i < 2*length; i++){
      s.putString(r+i,c," ",Terminal.Color.DEFAULT,Terminal.Color.BLACK);
    }

    for (int i = 0; i < 2*length; i++){
      s.putString(r+i,c+length," ",Terminal.Color.DEFAULT,Terminal.Color.BLACK);
    }

    for (int i = 0; i < length; i++){
      s.putString((r+length-1)*2,c+i," ",Terminal.Color.DEFAULT,Terminal.Color.BLACK);
    }
  }

  public static boolean isWalkable(int xcor, int ycor){
    if ((xcor == 1 || xcor == 60) && (ycor >= 3 && ycor <= 33)) return false;
    if ((ycor == 3 || ycor == 33) && (xcor >= 1 && xcor <= 60)) return false;
    return true;
  }

  public static boolean isPlaceable(int xcor, int ycor, List<Tile> road, List<TackShooter> TackShooters, List<FreezeTower> FreezeTowers){
    for (Tile x: road){
      if (x.getX() == xcor && x.getY() == ycor) return false;
    }

    for (TackShooter x: TackShooters){
      if (x.getX() == xcor && x.getY() == ycor) return false;
    }

    for (FreezeTower x: FreezeTowers){
      if (x.getX() == xcor && x.getY() == ycor) return false;
    }
    return true;
  }

  public static boolean isPlaceable(int xcor, int ycor, List<Tile> road){
    for (Tile x: road){
      if (x.getX() == xcor && x.getY() == ycor) return true;
    }
    return false;
  }

  public static void main(String[] args) throws FileNotFoundException {
    Terminal terminal = TerminalFacade.createTextTerminal();
    terminal.enterPrivateMode();

    TerminalSize size = terminal.getTerminalSize();
    terminal.setCursorVisible(false);

    int cursorX = 30;
    int cursorY = 18;

    boolean running = true;
    int mode = 1; //start off in pause mode
    int map = 1; //default map is Map 1
    if (args.length != 0) map = Integer.parseInt(args[0]); //choice to choose map 2 or 3
    long lastTime =  System.currentTimeMillis();
    long currentTime = lastTime; //timer syster
    long timer = 0;
    int toggle = 0; //one time check to see if user has started game
    int sinceTime = 0; //keep track of income

    List<Tile> road = new ArrayList<Tile>(); //stores the coordinates from map files for the road
    List<Tile> border = new ArrayList<Tile>();

    List<TackShooter> TackShooters = new ArrayList<TackShooter>(); //stores towers that have been placed on the map
    int TackShooterPrice = 150; //price for tackShooters
    int TackShooterDelay = 400; //delay between each tackshooter shot
    int TackShooterRad = 4;
    int TackShooterSinceTime = 0;

    List<Tack> tacks = new ArrayList<Tack>();
    int tackSinceTime = 0;
    int tackDelay = 75;

    List<Spike> spikes = new ArrayList<Spike>();
    int SpikePrice = 50;
    int SpikeLives = 5;

    List<FreezeTower> FreezeTowers = new ArrayList<FreezeTower>();
    int FreezeTowerPrice = 100;
    int FreezeTowerRad = 4;
    int FreezeTowerDelay = 2500;
    int FreezeTowerSinceTime = 0;
    int freezeTime = 500;

    int lives = 50; //user variables
    int money = 200;
    int income = 75;

    List<Balloon> balloons = new ArrayList<Balloon>();
    int balloonSinceTime = 0; //used to spawn a balloon every second
    int balloonMoveTime = 0; //used to move all balloons every two seconds
    int level = 1; //variables to be adjusted according to level
    int num_balloons = 5; //number of balloons to be initialized
    int balloons_made = 0; //number of balloons already initialized
    int balloon_lives = 1; //number of lives each balloon will have
    int balloon_delay = 600; //milliseconds between each balloon movement
    int balloon_spawnTime = 1200;
    boolean level_started = false;
    boolean all_spawned = false;

    //which tower is being placed down
    boolean tack_toggled = false;
    boolean spike_toggled = false;
    boolean ice_toggled = false;

    Screen s = new Screen(terminal);
    s.startScreen();
    s.setCursorPosition(null);
    s.clear();

    //instructions to play game
    s.putString(0,0,"Welcome to Bloons Tower Defense!",Terminal.Color.DEFAULT,Terminal.Color.DEFAULT,ScreenCharacterStyle.Bold);
    s.putString(0,2,"To begin the game, press b.",Terminal.Color.DEFAULT,Terminal.Color.DEFAULT);
    s.putString(0,3,"Once you have begun, press a to pause and b to resume the game.",Terminal.Color.DEFAULT,Terminal.Color.DEFAULT);
    s.putString(0,5,"Balloons will start spawning immediately and travel down the road.",Terminal.Color.DEFAULT,Terminal.Color.DEFAULT);
    s.putString(0,6,"To defeat them, place down towers by typing the letter  of the tower you want",Terminal.Color.DEFAULT,Terminal.Color.DEFAULT);
    s.putString(0,7,"to buy and using the arrow keys to give it a location on the grass, which are",Terminal.Color.DEFAULT,Terminal.Color.DEFAULT);
    s.putString(0,8,"the green tiles. Press the enter key to place the tower down. You will ",Terminal.Color.DEFAULT,Terminal.Color.DEFAULT);
    s.putString(0,9,"receive $75 every 10 seconds. Use your income wisely to purchase towers!",Terminal.Color.DEFAULT,Terminal.Color.DEFAULT);
    s.putString(0,10,"If any balloons reach the end of the road, your lives will decrease.",Terminal.Color.DEFAULT,Terminal.Color.DEFAULT);
    s.putString(0,11,"If your lives reach 0, you lose.",Terminal.Color.DEFAULT,Terminal.Color.DEFAULT);
    s.putString(0,13,"Best of luck!",Terminal.Color.DEFAULT,Terminal.Color.DEFAULT,ScreenCharacterStyle.Blinking);
    s.refresh();

    while (running){
    //  s.putString(cursorX,cursorY,"+",Terminal.Color.WHITE,Terminal.Color.BLACK);

      for (Tile x: road){
        x.draw(s);
      }

      for (TackShooter x: TackShooters){
        x.draw(s);
      }

      for (Tack x: tacks){
        x.draw(s);
      }

      for (Spike x: spikes){
        x.draw(s);
      }

      for (FreezeTower x: FreezeTowers){
        x.draw(s);
      }

      if (toggle > 0) s.putString(cursorX,cursorY,"+",Terminal.Color.WHITE,Terminal.Color.BLACK);

      balloonMoveTime += (currentTime - lastTime); //move balloons
      for(int i = balloons.size()-1; i >= 0; i--){
        Balloon x = balloons.get(i);
        x.draw(s);
          if (balloonMoveTime >= x.getSince() && x.getIsAlive()){
            if (x.getTile() < road.size()){
              x.move(road.get(x.getTile()), balloonMoveTime);

              if (x.getTile() == road.size()-1){ //when balloon reaches end of road
                x.makeDead();
                lives--;
                balloons.remove(i);
              }
            }
          }
      }

      s.refresh();

      s.putString(10,size.getRows()-2,"[To exit the game, press the escape key.]",Terminal.Color.DEFAULT,Terminal.Color.DEFAULT);
      if (mode == 0){
        lastTime = currentTime;
        currentTime = System.currentTimeMillis();
        timer += (currentTime - lastTime);//add the amount of time since the last frame
        s.putString(65,9,"Time: "+(timer /1000)+"            ",Terminal.Color.DEFAULT,Terminal.Color.DEFAULT);
        s.putString(65,10,"Lives Left: "+lives+"            ",Terminal.Color.DEFAULT,Terminal.Color.DEFAULT);
        s.putString(65,11,"Money: "+money+"            ",Terminal.Color.DEFAULT,Terminal.Color.DEFAULT);
        s.putString(65,5,"Level: "+level+"            ",Terminal.Color.DEFAULT,Terminal.Color.DEFAULT,ScreenCharacterStyle.Bold);
        s.putString(65,15, "Tower Key", Terminal.Color.BLACK,Terminal.Color.DEFAULT,ScreenCharacterStyle.Underline);
        s.putString(65,16,"TackShooter: key t, Price "+TackShooterPrice+", Radius "+TackShooterRad,Terminal.Color.DEFAULT,Terminal.Color.DEFAULT);
        s.putString(65,17,"FreezeTower: key f, Price "+FreezeTowerPrice+", Radius "+FreezeTowerRad,Terminal.Color.DEFAULT,Terminal.Color.DEFAULT);
        s.putString(65,18,"Spike:       key *, Price "+SpikePrice+", Hits "+SpikeLives,Terminal.Color.DEFAULT,Terminal.Color.DEFAULT);
        s.refresh();

        sinceTime += (currentTime - lastTime); //add the amount of time since the last frame
        if (sinceTime >= 10000 && timer != 0){
          money += income;
          sinceTime = 0;
        }

        balloonSinceTime += (currentTime - lastTime); //spawn in balloons
        if (balloonSinceTime >= balloon_spawnTime && balloons_made < num_balloons){
          level_started = true;
          balloons.add(new Balloon(balloon_lives, balloon_delay, road.get(0).getX(), road.get(0).getY()));
          balloons_made++;
          if (balloons_made == num_balloons) all_spawned = true;
          balloonSinceTime = 0;
        }

        if (balloons.size() == 0 && level_started && all_spawned){
          level++;
          num_balloons+=5;
          balloons_made=0;
          balloon_lives++;
          balloon_delay-=50;
          for (Tack x: tacks) x.setSteps(4); //reset tacks
          mode=1;
          level_started = false;
          all_spawned = false;
          s.putString(10,1,"Now commencing level "+level+". Press b to begin.",Terminal.Color.DEFAULT,Terminal.Color.DEFAULT,ScreenCharacterStyle.Blinking);
        }
        /*
        s.putString(65,25,"balloons: "+num_balloons,Terminal.Color.DEFAULT,Terminal.Color.DEFAULT);
        s.putString(65,26,"b_lives: "+balloon_lives,Terminal.Color.DEFAULT,Terminal.Color.DEFAULT);
        s.putString(65,27,"b_delay: "+balloon_delay,Terminal.Color.DEFAULT,Terminal.Color.DEFAULT);
        s.putString(65,28,"started: "+level_started,Terminal.Color.DEFAULT,Terminal.Color.DEFAULT);
        */
        s.refresh();

        TackShooterSinceTime  += (currentTime - lastTime); //create new tacks
        for (TackShooter x: TackShooters){
          if (TackShooterSinceTime >= x.getSince() && x.inRadius(balloons)){
            x.spawnTacks(tacks, TackShooterSinceTime, tackDelay);
          }
        }
        //s.putString(65,20,"Tack#: "+tacks.size(),Terminal.Color.DEFAULT,Terminal.Color.DEFAULT);

        tackSinceTime += (currentTime - lastTime); //fire tacks
        for (int i = tacks.size()-1; i>=0; i--){
          Tack x = tacks.get(i);
          if (tackSinceTime >= x.getSince()){ //creates delay between tacks shot
            x.undraw(s, x.getX(), x.getY(), road);
            x.move(tackSinceTime);
            x.hitTarget(balloons);
            if (x.getSteps() >= TackShooterRad){
              tacks.remove(i);
            }
          }
        }

        for (int i = spikes.size()-1; i>=0; i--){ //see if spikes hit anything
          Spike x = spikes.get(i);
          if (x.getLives() == 0) spikes.remove(i);
          x.hitTarget(balloons);
        }

        s.refresh();
      }

      if (mode == 1){ //pause timer
        lastTime = System.currentTimeMillis();
        currentTime = System.currentTimeMillis();
        if (toggle >= 1) s.putString(65,9,"Time: " + (timer / 1000),Terminal.Color.DEFAULT,Terminal.Color.DEFAULT);
      }

      Key key = s.readInput();
      if (key != null){ //what to start doing when key is pressed

        toggle++;
        if (toggle == 1){ //one-time execution
          s.clear();

          File f = new File("map0.txt");
          if (map == 1) f = new File("map1.txt"); //different choices
          if (map == 2) f = new File("map2.txt");
          if (map == 3) f = new File("map3.txt");
          Scanner in = new Scanner(f);
          while (in.hasNext()){ //read in coordinates
            String line = in.nextLine();
            String[] arr = line.split(" ");
            int xcor = Integer.parseInt(arr[0]);
            int ycor = Integer.parseInt(arr[1]);
            road.add(new Tile(xcor, ycor));
          }
        }

        if (toggle >= 1 && mode == 1){
          drawBorder(1,3,30,s);

          for (int x = 2; x < 60; x++){ //color in background
            for (int y = 4; y < 33; y++){
              s.putString(x,y," ",Terminal.Color.DEFAULT,Terminal.Color.GREEN);
            }
          }
        }


        if (key.getKind() == Key.Kind.Escape){ //exit game
          s.stopScreen();
          running = false;
        }

        if (mode == 1 && key.getCharacter() == 'b'){ //enter game mode
          mode--;
        }

        if (mode == 0 && key.getCharacter() == 'a'){ //pause game
          mode++;
        }

         //test code for moving around towers
        if (toggle > 0 && key.getKind() == Key.Kind.ArrowUp){
          if (isWalkable(cursorX, cursorY-1)){
            cursorY--;
              if (isPlaceable(cursorX,cursorY+1,road,TackShooters,FreezeTowers)) s.putString(cursorX,cursorY+1," ",Terminal.Color.DEFAULT,Terminal.Color.GREEN);
          }
        }

        if (toggle > 0 && key.getKind() == Key.Kind.ArrowDown){
          if (isWalkable(cursorX, cursorY+1)){
            cursorY++;
            if (isPlaceable(cursorX,cursorY-1,road,TackShooters,FreezeTowers)) s.putString(cursorX,cursorY-1," ",Terminal.Color.DEFAULT,Terminal.Color.GREEN);
          }
        }

        if (toggle > 0 && key.getKind() == Key.Kind.ArrowLeft){
          if (isWalkable(cursorX-1, cursorY)){
            cursorX--;
            if (isPlaceable(cursorX+1,cursorY,road,TackShooters,FreezeTowers)) s.putString(cursorX+1,cursorY," ",Terminal.Color.DEFAULT,Terminal.Color.GREEN);
          }
        }

        if (toggle > 0 && key.getKind() == Key.Kind.ArrowRight){
          if (isWalkable(cursorX+1, cursorY)){
            cursorX++;
            if (isPlaceable(cursorX-1,cursorY,road,TackShooters,FreezeTowers)) s.putString(cursorX-1,cursorY," ",Terminal.Color.DEFAULT,Terminal.Color.GREEN);
          }
        }

        if (toggle > 0 && key.getCharacter() == 't'){
          tack_toggled = true;
          spike_toggled = false;
          ice_toggled = false;
        }

        if (toggle > 0 && key.getCharacter() == '*'){
          tack_toggled = false;
          spike_toggled = true;
          ice_toggled = false;
        }

        if (toggle > 0 && key.getCharacter() == 'f'){
          tack_toggled = false;
          spike_toggled = false;
          ice_toggled = true;
        }

        if (toggle >= 1 && key.getKind() == Key.Kind.Enter){
          if (tack_toggled){
            if (isPlaceable(cursorX,cursorY,road,TackShooters,FreezeTowers) && (money - TackShooterPrice >= 0)){
              TackShooters.add(new TackShooter(cursorX,cursorY,TackShooterPrice,TackShooterDelay,TackShooterRad));
              money -= TackShooterPrice;
              if (cursorX == 59) cursorX--;
              else cursorX++;
            }
          }

          if (spike_toggled){
            if (isPlaceable(cursorX,cursorY,road) && (money - SpikePrice >= 0)){
              spikes.add(new Spike(cursorX,cursorY,SpikePrice,SpikeLives));
              money -= SpikePrice;
              if (cursorX == 59) cursorX--;
              else cursorX++;
            }
          }

          if (ice_toggled){
            if (isPlaceable(cursorX,cursorY,road,TackShooters,FreezeTowers) && (money - FreezeTowerPrice >= 0)){
              FreezeTowers.add(new FreezeTower(cursorX,cursorY,FreezeTowerPrice,FreezeTowerDelay,FreezeTowerRad));
              money -= FreezeTowerPrice;
              if (cursorX == 59) cursorX--;
              else cursorX++;
            }
          }
        }

      }
    }
  }
}
