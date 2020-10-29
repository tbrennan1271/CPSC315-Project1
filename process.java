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
    burst = new int[10];
  }
}
