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
  public void attack(List<Balloon> balls){
    for(int i = 0; i < balls.size(); i++){
      Balloon temp = balls.get(i);
      if(temp.getX() == this.getX() && temp.getY() == this.getY()){
        temp.setLives(temp.getLives() - 1);
      }
    }
  }
}
