/*
* code that will maintain a single process
*
* @author Gabby Rodgers
* @author Tyler Brennan
*/

public class process{
  public char id;
  public String priority;
  public int arrival;
  public int[] burst;
  public process(){
    id = ' ';
    priority = "";
    arrival = 0;
    burst = new int[10];      // There will only ever be a maximum of 10 bursts
  }
  public String toString(){
    String res = "";
    res += "ID: " + id;
    res += "\nPriority: " + priority;
    res += "\nArrival time: " + arrival;
    for(int i = 0; i < burst.length; i++){
      if(i%2 == 0){
        res += "\nBurst: " + burst[i];
      } else{
        res += "\nBlock: " + burst[i];
      }
    }
    return res + "\n";
  }
}
