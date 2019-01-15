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

  /*
  //puts down a string at the specified location
  public static void putString(int r, int c,Terminal t, String s){
    t.moveCursor(r,c);
    for(int i = 0; i < s.length();i++){
      t.putCharacter(s.charAt(i));
    }
  }
  */

  public static void drawBorder(int r, int c, int length, Screen s){ //draws a border for the game, terminal must be at least 80 x 38
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

  public static void main(String[] args) throws FileNotFoundException {
    Terminal terminal = TerminalFacade.createTextTerminal();
    terminal.enterPrivateMode();

    TerminalSize size = terminal.getTerminalSize();
    terminal.setCursorVisible(false);

    int cursorX = 5;
    int cursorY = 4;

    boolean running = true;
    int mode = 1; //start off in pause mode
    int map = 1; //default map is Map 1
    if (args.length != 0) map = Integer.parseInt(args[0]); //choice to choose map 2 or 3
    long lastTime =  System.currentTimeMillis();
    long currentTime = lastTime; //timer syster
    long timer = 0;
    int toggle = 0; //one time check to see if user has started game
    int balloonSinceTime = 0; //used to spawn a balloon every second
    int balloonMoveTime = 0; //used to move all balloons every two seconds
    int sinceTime = 0;
    List<Tile> road = new ArrayList<Tile>(); //stores the coordinates from map files for the road
    List<Tile> border = new ArrayList<Tile>();

    int lives = 50; //user variables
    int money = 200;
    int income = 75;

    List<Balloon> balloons = new ArrayList<Balloon>();
    int level = 1; //variables to be adjusted according to level
    int num_balloons = 5; //number of balloons to be initialized
    int balloons_made = 0; //number of balloons already initialized
    int balloon_lives = 1; //number of lives each balloon will have
    int balloon_delay = 100; //milliseconds between each balloon movement
    boolean level_passed = false;
    Screen s = new Screen(terminal);
    s.startScreen();
    s.setCursorPosition(null);
    s.clear();

    //instructions to play game
    s.putString(0,0,"Welcome to Bloons Tower Defense!",Terminal.Color.BLACK,Terminal.Color.DEFAULT,ScreenCharacterStyle.Bold);
    s.putString(0,2,"To begin the game, press b.",Terminal.Color.BLACK,Terminal.Color.DEFAULT);
    s.putString(0,3,"Once you have begun, press a to pause and b to resume the game.",Terminal.Color.BLACK,Terminal.Color.DEFAULT);
    s.putString(0,5,"Balloons will start spawning immediately and travel down the road.",Terminal.Color.BLACK,Terminal.Color.DEFAULT);
    s.putString(0,6,"To defeat them, place down towers by typing the letter  of the tower you want",Terminal.Color.BLACK,Terminal.Color.DEFAULT);
    s.putString(0,7,"to buy and using the arrow keys to give it a location on the grass, which are",Terminal.Color.BLACK,Terminal.Color.DEFAULT);
    s.putString(0,8,"the green tiles. Press the enter key to place the tower down. You will ",Terminal.Color.BLACK,Terminal.Color.DEFAULT);
    s.putString(0,9,"receive $75 every 10 seconds. Use your income wisely to purchase towers!",Terminal.Color.BLACK,Terminal.Color.DEFAULT);
    s.putString(0,10,"If any balloons reach the end of the road, your lives will decrease.",Terminal.Color.BLACK,Terminal.Color.DEFAULT);
    s.putString(0,11,"If your lives reach 0, you lose.",Terminal.Color.BLACK,Terminal.Color.DEFAULT);
    s.putString(0,13,"Best of luck!",Terminal.Color.BLACK,Terminal.Color.DEFAULT,ScreenCharacterStyle.Blinking);
    s.refresh();

    while (running){

      s.putString(0,size.getRows()-2,"[To exit the game, press the escape key.]",Terminal.Color.BLACK,Terminal.Color.DEFAULT);
      if (mode == 0){
        lastTime = currentTime;
        currentTime = System.currentTimeMillis();
        timer += (currentTime - lastTime);//add the amount of time since the last frame
        s.putString(65,9,"Time: "+(timer /1000),Terminal.Color.BLACK,Terminal.Color.DEFAULT);
        s.putString(65,10,"Lives Left: "+lives,Terminal.Color.BLACK,Terminal.Color.DEFAULT);
        s.putString(65,11,"Money: "+money,Terminal.Color.BLACK,Terminal.Color.DEFAULT);
        s.putString(65,5,"Level: "+level,Terminal.Color.BLACK,Terminal.Color.DEFAULT,ScreenCharacterStyle.Bold);
        //s.putString(65,14,"Made: "+ balloons.size(),Terminal.Color.BLACK,Terminal.Color.DEFAULT);
        s.refresh();

        sinceTime += (currentTime - lastTime); //add the amount of time since the last frame
        if (sinceTime >= 10000 && timer != 0){
          money += income;
          sinceTime = 0;
        }

        balloonSinceTime += (currentTime - lastTime); //spawn in balloons
        if (balloonSinceTime >= 1000 && balloons_made < num_balloons){
          balloons.add(new Balloon(balloons_made, balloon_lives, balloon_delay, road.get(0).getX(), road.get(0).getY()));
          balloons_made++;
          balloonSinceTime = 0;
        }

        for (Tile x: road){
          x.draw(s);
        }

        balloonMoveTime += (currentTime - lastTime); //move balloons
        for(int i = balloons.size()-1; i >= 0; i--){
        //  s.putString(65, 18,t"sinceTime: "+x.getSince()+" bmt: "+balloonMoveTime,Terminal.Color.BLACK,Terminal.Color.DEFAULT);
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

      }

      if (mode == 1){ //pause timer
        lastTime = System.currentTimeMillis();
        currentTime = System.currentTimeMillis();
        if (toggle >= 1) s.putString(65,9,"Time: " + (timer / 1000),Terminal.Color.BLACK,Terminal.Color.DEFAULT);
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

          //test code for placing down towers
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
        if (toggle >= 1 && key.getKind() == Key.Kind.ArrowUp){
          if (isWalkable(cursorX, cursorY-1)){
            cursorY--;
            terminal.moveCursor(cursorX,cursorY);
            terminal.putCharacter(' ');
          }
        }

        if (toggle >= 1 && key.getKind() == Key.Kind.ArrowDown){
          if (isWalkable(cursorX, cursorY+1)){
            cursorY++;
            terminal.moveCursor(cursorX,cursorY);
            terminal.putCharacter(' ');
          }
        }

        if (toggle >= 1 && key.getKind() == Key.Kind.ArrowLeft){
          if (isWalkable(cursorX-1, cursorY)){
            cursorX--;
            terminal.moveCursor(cursorX,cursorY);
            terminal.putCharacter(' ');
          }
        }

        if (toggle >= 1 && key.getKind() == Key.Kind.ArrowRight){
          if (isWalkable(cursorX+1, cursorY)){
            cursorX++;
            terminal.moveCursor(cursorX,cursorY);
            terminal.putCharacter(' ');
          }
        }

      }
    }
  }
}
