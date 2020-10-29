/*
*
*
* @author Gabby Rodgers
* @author Tyler Brennan
*/

import java.io.File;
import java.util.Scanner;
import java.io.FileInputStream;
import java.util.LinkedList;
public class systemSimulation{
  /* GLOBAL VARIABLES */
  public static LinkedList<process> processInput = new LinkedList<process>(); // List of processes saved as a LinkedList because we do not know the maximum number of processes
  private static int l2Quant;
  private static int l3Quant;

  public systemSimulation(){
  }

  /*
  * input
  * Input code parses the input file and breaks it into the corresponding sections of different processes
  * @param file - The file name pulled from the command line (file should be saved in the same folder as the code)
  */
  private static void input(String file){
    try{
      Scanner reader = new Scanner(new FileInputStream(file));
      process current = new process();  // Creates new processes to append to processInput
      l2Quant = reader.nextInt();       // The quantums for each low priority queue are always found at the start of the input file
      l3Quant = reader.nextInt();
      int count = 0;                    // Keeps track of position in the input file, as they are all organized the same way
      int tempInt;
      while(reader.hasNext()){
        if (count > 1){                 // Records the burst length/blocked time and checks if the process has ended
          tempInt = reader.nextInt();
          if(tempInt != -1){
            current.burst[count - 2] = tempInt;
          } else {
            count = -1;
            processInput.add(current);
            current = new process();
          }
        } else if(count == 1)           // Records the priority
          current.priority = reader.next();
        else if(count == 0)             // Records the ID
          current.id = reader.next().charAt(0);
        count ++;
      }
    }
    catch(Exception e){
      System.out.println("ERROR: Input a proper file");
    }
  }
  public static void main(String[] args){

    // first check to see if the program was run with the command line argument
    if(args.length < 1) {
      System.out.println("Error, usage: java ClassName inputfile");
      System.exit(1);
    } else {
      input(args[0]);
    }
  }
}
