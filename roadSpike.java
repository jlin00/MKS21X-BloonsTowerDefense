public class roadSpike extends Tower{

  /**A Tower constructor
  *@param int xCord is the x position of the tower, also its row in the array
  *@param int yCord is the y position of the tower, also its column in the array
  *@param int money is the cost
  *@param int rad is the radius
  */
  public roadSpike(int xCord, int yCord, int money, int rad){
    x = xCord;
    y = yCord;
    cost = money;
    radius = rad;
  }

  /**A method that takes one life from the balloon target on its tile
  */
  public void attack(){
    if(this.getX() == target.getX() && this.getY() == target.getY()){
        target.setLives(target.getLives() - 1);
    }
  }
}
