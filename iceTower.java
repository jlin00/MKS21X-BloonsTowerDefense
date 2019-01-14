import java.util.*;
public class iceTower extends Tower{

  /**A Tower constructor
  *@param int xCord is the x position of the tower, also its row in the array
  *@param int yCord is the y position of the tower, also its column in the array
  *@param int money is the cost
  *@param int rad is the radius
  */
  public iceTower(int xCord, int yCord, int money, int rad){
    x = xCord;
    y = yCord;
    cost = money;
    radius = rad;
  }

  public void attack(List<Balloon> ball){
    for(int i = 0; i < ball.size(); i++){
      Balloon temp = ball.get(i);
      double distance = Math.pow(this.getX() - temp.getX(), 2) + Math.pow(this.getY() - temp.getY(), 2);
      if(temp.getIsAlive() && temp.getInit()){
        if(distance <= (radius * radius)){
          temp.setDelay(5000);
        }
      }
    }
  }
}
