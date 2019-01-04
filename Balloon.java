public class Balloon{
  private int ID, lives, speed;
  private boolean isAlive;

  public Balloon(int num){
    ID = num;
    lives = 1;
    isAlive = true;
  }

  public Balloon(int num, int numLives){
    ID = num;
    lives = numLives;
    isAlive = true;
  }

  public int getLives(){
    return lives;
  }

  public int getSpeed(){
    return speed;
  }

  public boolean getIsAlive(){
    return isAlive;
  }

  public void setLives(int numLives){
    lives = numLives;
  }

  public void setSpeed(int num){
    speed = num;
  }

  public void makeDead(){
    isAlive = false;
  }
}
