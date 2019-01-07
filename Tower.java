public abstract class Tower{
  private int x, y;
  private int cost;
  private int radius;

  /**A Tower constructor
  *@param int xCord is the x position of the tower, also its row in the array
  *@param int yCord is the y position of the tower, also its column in the array
  *@param int money is the cost
  *@param int rad is the radius
  */
  public Tower(int xCord, int yCord, int money, int rad){
    x = xCord;
    y = yCord;
    cost = money;
    radius = rad;
  }

  /**A method to get the x value, or the row of the tower in the array
  *@return int
  */
  public int getX(){
    return x;
  }

  /**A method to get the y value, or the column of the tower in the array
  *@return int
  */
  public int getY(){
    return y;
  }

  /**A method to get the cost of the tower
  *@return int
  */
  public int getCost(){
    return cost;
  }

  /**A method to get the radius of the tower
  *@return int
  */
  public int getRadii(){
    return radius;
  }

  /**A method to be written by the individual towers
  *attacks are different for each type of tower
  */
  abstract void attack();

  /**A method to find a balloon target for the tower
  *the balloon target is the nearest balloon within the radius of the tower
  *@return Balloon
  */
  abstract Balloon findTarget();
}
