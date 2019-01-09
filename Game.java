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

  //erases an area of the terminal where there may have been characters
  /*
  public static void clear(Terminal t){
    t.moveCursor(1,2);
    int x = t.getTerminalSize().getRows() + 1;
    int y = t.getTerminalSize().getColumns() + 1;
    //System.out.println(t.getTerminalSize()); //to check area for debugging purposes
    int area = x * y;
    //System.out.println(area); //debugging purposes
    for (int i = 0; i < area; i++){
      t.putCharacter(' '); //clears terminal
    }
    }
  */

  public static void drawBorder(int r, int c, Terminal t, int length){
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
    int mode = 1; //game mode
    long lastTime =  System.currentTimeMillis();
    long currentTime = lastTime;
    long timer = 0;
    long seconds = 0;

    int toggle = 0; //one time check to see if user has started game
    List<Tile> road = new ArrayList<Tile>();

    int lives = 50;
    int money = 200;
    int income = 100;

    while(running){
      Key key = terminal.readInput();

      if (key == null){
        if (toggle == 0) putString(1,1,terminal,"This is the start screen. Press the ArrowDown key twice to load the game."); //start screen
      }

      if (key != null){
        toggle++;
        if (toggle == 1) {
          terminal.clearScreen();
          drawBorder(1,3, terminal, 30);
        }
        if (mode == 0){
          putString(0,2,terminal,"Game Started. Press ArrowUp once to pause.   "); //game mode

          File f = new File("map1.txt");
          Scanner in = new Scanner(f);
          while (in.hasNext()){
            String line = in.nextLine();
            String[] arr = line.split(" ");
            int xcor = Integer.parseInt(arr[0]);
            int ycor = Integer.parseInt(arr[1]);
            road.add(new Tile(xcor, ycor));
          }

          for (int x = 2; x < 60; x++){
            for (int y = 4; y < 33; y++){
              terminal.moveCursor(x,y);
              terminal.applyBackgroundColor(Terminal.Color.GREEN);
              terminal.putCharacter(' ');
              terminal.applyBackgroundColor(Terminal.Color.DEFAULT);
              terminal.applyForegroundColor(Terminal.Color.DEFAULT);
            }
          }

          for (int i = 0; i < road.size(); i++){
            terminal.moveCursor(road.get(i).getX(), road.get(i).getY());
            terminal.applyBackgroundColor(Terminal.Color.WHITE);
            terminal.putCharacter(' ');
            terminal.applyBackgroundColor(Terminal.Color.DEFAULT);
            terminal.applyForegroundColor(Terminal.Color.DEFAULT);
          }

          if (key.getKind() == Key.Kind.ArrowUp){
            mode++;
          }
        }

        if (mode != 0){ //pause
          putString(0,2,terminal,"Game Paused. Press ArrowDown twice to resume.");
          lastTime = System.currentTimeMillis();
          currentTime = System.currentTimeMillis();
          if (key.getKind() == Key.Kind.ArrowDown){
            mode--;
          }
        }

        if (key.getKind() == Key.Kind.Escape) {
          terminal.exitPrivateMode();
          running = false;
        }
      }
      if(mode == 0){
        lastTime = currentTime;
        currentTime = System.currentTimeMillis();
        timer += (currentTime - lastTime);//add the amount of time since the last frame
        putString(65,9,terminal,"Time: "+(timer /1000));
        putString(65,10,terminal,"Lives Left: "+lives);
        putString(65,11,terminal,"Money: "+money);
      }
      if (mode == 1){
        putString(65,9,terminal,"Time: " + (timer / 1000));
      }
    }
  }
}
