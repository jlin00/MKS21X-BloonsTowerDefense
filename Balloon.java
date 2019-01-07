public class Balloon{
  private int ID, lives, speed;
  private boolean isAlive;

  /**A Balloon constructor
  *@param int each balloon has a given ID number to differentiate it easily
  *the default number of lives is 1
  */
  public Balloon(int num){
    ID = num;
    lives = 1;
    isAlive = true;
  }

  /**A second Balloon constructor
  *@param int each balloon has a given ID nummber
  *@param int number of lives can be determined by the user
  */
  public Balloon(int num, int numLives){
    ID = num;
    lives = numLives;
    isAlive = true;
  }

  /**A method to get the number of lives a balloon has
  *@return int lives
  */
  public int getLives(){
    return lives;
  }

  /**A method to get the speed of a balloon, in terms of movement across the road
  *@return int speed
  */
  public int getSpeed(){
    return speed;
  }

  /**A method to check if a balloon is alive
  *@return boolean isAlive
  */
  public boolean getIsAlive(){
    return isAlive;
  }

  /**A method to change the number of lives a balloon has
  *@param int numLives
  */
  public void setLives(int numLives){
    lives = numLives;
  }

  /**A method to change the speed of a balloon
  *@param int speed
  */
  public void setSpeed(int num){
    speed = num;
  }

  /**A method to make a balloon 'dead' by changing the isAlive boolean to false
  */
  public void makeDead(){
    isAlive = false;
  }
}
