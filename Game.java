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
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.*;

public class Game {
  //puts down a string at the specified location
  public static void putString(int r, int c,Terminal t, String s){
    t.moveCursor(r,c);
    for(int i = 0; i < s.length();i++){
      t.putCharacter(s.charAt(i));
    }
  }

  public static void drawBorder(int r, int c, Terminal t, int length){ //draws a border for the game, terminal must be at least 80 x 34
    for (int i = 0; i < length; i++){
      t.moveCursor (r, c + i);
      t.applyBackgroundColor(Terminal.Color.BLACK);
      t.putCharacter(' ');
      t.applyBackgroundColor(Terminal.Color.DEFAULT);
      t.applyForegroundColor(Terminal.Color.DEFAULT);
    }
    for (int i = 0; i < 2*length; i++){
      t.moveCursor (r + i, c);
      t.applyBackgroundColor(Terminal.Color.BLACK);
      t.putCharacter(' ');
      t.applyBackgroundColor(Terminal.Color.DEFAULT);
      t.applyForegroundColor(Terminal.Color.DEFAULT);
    }

    for (int i = 0; i < 2*length; i++){
      t.moveCursor (r + i, c + length);
      t.applyBackgroundColor(Terminal.Color.BLACK);
      t.putCharacter(' ');
      t.applyBackgroundColor(Terminal.Color.DEFAULT);
      t.applyForegroundColor(Terminal.Color.DEFAULT);
    }

    for (int i = 0; i < length; i++){
      t.moveCursor ((r + length - 1)*2, c + i);
      t.applyBackgroundColor(Terminal.Color.BLACK);
      t.putCharacter(' ');
      t.applyBackgroundColor(Terminal.Color.DEFAULT);
      t.applyForegroundColor(Terminal.Color.DEFAULT);
    }
  }

  public static void main(String[] args) throws FileNotFoundException {
    Terminal terminal = TerminalFacade.createTextTerminal();
    terminal.enterPrivateMode();

    TerminalSize size = terminal.getTerminalSize();
    terminal.setCursorVisible(false);

    boolean running = true;
    int mode = 1; //start off in pause mode
    int map = 1; //default map is Map 1
    if (args.length != 0) map = Integer.parseInt(args[0]); //choice to choose map 2
    long lastTime =  System.currentTimeMillis();
    long currentTime = lastTime; //timer syster
    long timer = 0;
    int toggle = 0; //one time check to see if user has started game
    int balloonSinceTime = 0; //used to spawn a balloon every second
    int balloonMoveTime = 0; //used to move all balloons every two seconds
    int sinceTime = 0;
    List<Tile> road = new ArrayList<Tile>(); //stores the coordinates from map files for the road

    int lives = 50; //user variables
    int money = 200;
    int income = 75;

    List<Balloon> balloons = new ArrayList<Balloon>();
    int level = 1; //variables to be adjusted according to level
    int num_balloons = 5; //number of balloons to be initialized
    int balloons_made = 0; //number of balloons already initialized
    int balloon_lives = 1; //number of lives each balloon will have
    int balloon_delay = 800;
    boolean level_passed = false;

    putString(0,0,terminal,"This is the start screen. Press b to begin.");
    putString(0,1,terminal,"Once the game is running, press a to pause and b to resume.");

    while (running){
      if (mode == 0){
        lastTime = currentTime;
        currentTime = System.currentTimeMillis();
        timer += (currentTime - lastTime);//add the amount of time since the last frame
        putString(65,9,terminal,"Time: "+(timer /1000));
        putString(65,10,terminal,"Lives Left: "+lives);
        putString(65,11,terminal,"Money: "+money);

        sinceTime += (currentTime - lastTime); //add the amount of time since the last frame
        if (sinceTime >= 10000 && timer != 0){
          money += income;
          sinceTime = 0;
        }

        balloonSinceTime += (currentTime - lastTime);
        if (balloonSinceTime >= 1000 && balloons_made < num_balloons){
          balloons.add(new Balloon(balloons_made, balloon_lives, balloon_delay, 5, 4));
          balloons_made++;
          balloonSinceTime = 0;
        }

        balloonMoveTime += (currentTime - lastTime);
        for(Balloon x: balloons){
          if (balloonMoveTime >= x.getDelay()){
            int temp = x.getTile();
            if (temp < road.size()){
              x.move(road.get(temp));
              x.draw(terminal);

              if (temp != 0){
                road.get(temp - 1).draw(terminal);
              }
            }
            balloonMoveTime = 0;
          }
        }

      }

      if (mode == 1){
        lastTime = System.currentTimeMillis();
        currentTime = System.currentTimeMillis();
        putString(65,9,terminal,"Time: " + (timer / 1000));
      }

      Key key = terminal.readInput();

      if (key != null){
        toggle++;
        if (toggle == 1){
          terminal.clearScreen();
          drawBorder(1,3, terminal, 30);

          File f = new File("map0.txt");
          if (map == 1) f = new File("map1.txt");
          if (map == 2) f = new File("map2.txt");
          Scanner in = new Scanner(f);
          while (in.hasNext()){ //read in coordinates
            String line = in.nextLine();
            String[] arr = line.split(" ");
            int xcor = Integer.parseInt(arr[0]);
            int ycor = Integer.parseInt(arr[1]);
            road.add(new Tile(xcor, ycor));
          }

          for (int x = 2; x < 60; x++){ //color in background
            for (int y = 4; y < 33; y++){
              terminal.moveCursor(x,y);
              terminal.applyBackgroundColor(Terminal.Color.GREEN);
              terminal.putCharacter(' ');
              terminal.applyBackgroundColor(Terminal.Color.DEFAULT);
              terminal.applyForegroundColor(Terminal.Color.DEFAULT);
            }
          }

          for (Tile x: road){ //color in roads
            x.draw(terminal);
          }

        }

        if (key.getKind() == Key.Kind.Escape){
          terminal.exitPrivateMode();
          running = false;
        }

        if (mode == 1 && key.getCharacter() == 'b'){
          mode--;
        }

        if (mode == 0 && key.getCharacter() == 'a'){
          mode++;
        }

      }


    }
  }
}
