import java.util.*;

public class tackShooter extends Tower{

  private List<Tack> tacks = new ArrayList<Tack>();

  /**A Tower constructor
  *@param int xCord is the x position of the tower, also its row in the array
  *@param int yCord is the y position of the tower, also its column in the array
  *@param int money is the cost
  *@param int rad is the radius
  */
  public tackShooter(int xCord, int yCord, int money, int rad){
    x = xCord;
    y = yCord;
    cost = money;
    radius = rad;
  }

  /**A method that creates four tacks and calls for them to move and attack
  *once the tack reaches outside of the radius, it is removed from the list of tacks
  */
  public void attack(){
    tacks.add(new Tack(this.getX(), this.getY(), 1));
    tacks.add(new Tack(this.getX(), this.getY(), 2));
    tacks.add(new Tack(this.getX(), this.getY(), 3));
    tacks.add(new Tack(this.getX(), this.getY(), 4));
    for(int i = 0; i < tacks.size(); i++){
      Tack temp = tacks.get(i);
      if(temp.getSteps() > this.getRadii()){
        tacks.remove(i);
      }
      if(temp.getSteps() < this.getRadii()){
        temp.move();
      }
    }
  }
}
