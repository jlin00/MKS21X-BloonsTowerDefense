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
    boolean startingMode = true; //first mode, starting screen, will be filled with instructions

    long tStart = System.currentTimeMillis(); //timer system
    long lastSecond = 0;

    while(running){
      Key key = terminal.readInput();

      if (startingMode) putString(1,4,terminal,"Press up to start"); //default starting mode
      if (key != null){
        if (key.getKind() == Key.Kind.Escape){
          terminal.exitPrivateMode();
          running = false;
        }
        if (startingMode && key.getKind() == Key.Kind.ArrowUp){ //exit starting mode into game mode
          startingMode = false;
          terminal.clearScreen();
        }
        if (!startingMode){ //enter game mode
          //terminal.applyBackgroundColor(Terminal.Color.WHITE);
    			//terminal.applyForegroundColor(Terminal.Color.BLACK);
          putString(1,4,terminal,"Game Started");
          File f = new File("map1.txt");
          Scanner in = new Scanner(f);
          while (in.hasNext()){
            String line = in.nextLine();
            //System.out.println(line.substring(0, 1)); //debugging purposes
            //System.out.println(line.substring(2));
            int x = Integer.parseInt(line.substring(0, 1));
            int y = Integer.parseInt(line.substring(2));

            terminal.moveCursor(x, y);
            terminal.applyBackgroundColor(Terminal.Color.WHITE);
            terminal.applyBackgroundColor(Terminal.Color.RED);
          }
        }
      }
      long tEnd = System.currentTimeMillis(); //returns timer, for debugging purposes
      long millis = tEnd - tStart;
      if(millis/1000 >= lastSecond){
        lastSecond = millis / 1000;
        //one second has passed.
        putString(0,0,terminal,"Seconds since start of program: "+lastSecond);
      }
    }


  }
}
