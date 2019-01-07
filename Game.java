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

  public static void main(String[] args) throws FileNotFoundException {
    Terminal terminal = TerminalFacade.createTextTerminal();
    terminal.enterPrivateMode();

    TerminalSize size = terminal.getTerminalSize();
    terminal.setCursorVisible(false);

    boolean running = true;
    int mode = 0; //game mode
    long lastTime =  System.currentTimeMillis();
    long currentTime = lastTime;
    long timer = 0;

    while(running){
      Key key = terminal.readInput();
      if (key == null){
        putString(1,1,terminal,"This is the start screen. Press any key to begin.");
      }

      if (key != null){
        terminal.clearScreen();
        if (mode == 0){
          putString(1,4,terminal,"Game Started"); //game mode
          File f = new File("map1.txt");
          Scanner in = new Scanner(f);
          while (in.hasNext()){
            String line = in.nextLine();
            //System.out.println(line.substring(0, 1)); //debugging purposes
            //System.out.println(line.substring(2));
            int x = Integer.parseInt(line.substring(0, 1));
            int y = Integer.parseInt(line.substring(2));

            terminal.moveCursor(x,y);
            terminal.applyBackgroundColor(Terminal.Color.WHITE);
            terminal.applyForegroundColor(Terminal.Color.RED);
            terminal.applyBackgroundColor(Terminal.Color.DEFAULT);
            terminal.applyForegroundColor(Terminal.Color.DEFAULT);
          }

          if (key.getKind() == Key.Kind.ArrowUp){
            mode++;
          }
        }

        if (mode != 0){ //pause
          putString(1,4,terminal,"Game Paused ");
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
        putString(0,0,terminal,"Seconds since start of program: "+(timer / 1000));
      }
      if (mode == 1){
        putString(0,0,terminal,"Seconds since start of program: " + (timer / 1000));
      }
    }
  }
}
