public class Tack{
  private int x, y;
  private int direction;
  private int steps;

  /**A constructor of a tack owned by a tackShooter
  *@param int xCord is the x coordinate
  *@param int yCord is the y coordinate
  *@param int direx is the direction of the tack
               1 is up
               2 is right
               3 is down
               4 is left
  */
  public Tack(int xCord, int yCord, int direx){
    x = xCord;
    y = yCord;
    direction = direx;
  }

  /**A method that will make the tack move according to its direction and attack balloons as it goes
  */
  public void move(){
    for(int i = 0; i < balloons.size(); i++){
      Balloon temp = balloons.get(i);
      if(temp.getIsAlive() && temp.getInit()){
        if(temp.getX() == this.getX() && temp.getY() == this.getY()){
          temp.setLives(temp.getLives() - 1);
          if(temp.getLives() == 0){
            temp.makeDead();
          }
        }
      }
    }
    if(direction == 1){
      y -= 1;
      steps++;
    }
    if(direction == 2){
      x += 1;
      steps++;
    }
    if(direction == 3){
      y += 1;
      steps++;
    }
    if(direction == 4){
      x -= 1;
      steps++;
    }
  }

  /**A method to get the tack's x coordinate
  */
  public int getX(){
    return x;
  }

  /**A method to get the tack's y coordinate
  */
  public int getY(){
    return y;
  }

  /**A method to get the tack's steps
  */
  public int getSteps(){
    return steps;
  }

  /**A method to get a tack's direction
  *@return int 1 is up
               2 is right
               3 is down
               4 is left
  */
  public int getDirection(){
    return direction;
  }

}
