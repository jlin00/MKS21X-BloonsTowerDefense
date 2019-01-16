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

  /*placing an iceTower on the terminal, to be placed in the GameScreen class
  if(toggle >= 1 && key.getCharacter() == 'i'){
    if(isPlaceable(cursorX, cursorY, road, IceTowers) && (money - IceTowerPrice >= 0){
      IceTowers.add(new iceTower(cursorX, cursorY, IceTowerPrice, IceTowerRad));
      money -= IceTowerPrice;
      cursorX++;
  }
  */

  //s.putString(tba,tba,"IceTower: key I, Price "+IceTowerPrice+", Radius "+IceTowerRad+"",Terminal.Color.DEFAULT,Terminal.Color.DEFAULT);
}
