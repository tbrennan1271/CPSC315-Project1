/*
* code that will maintain a single process
*
* @author Gabby Rogers
* @author Tyler Brennan
*/

public class process{
    public char id;
    public String priority;
    public int arrival;
    public int[] burst;
    public int index;   // Hopefully temp var to maintain burst index
    public int l2Quant;
    public int l3Quant;
    public int numOfPreemptions;

    /*
    * process
    * Initializes the global variables and the priority queues to be used
    */
    public process(){
        id = ' ';
        priority = "";
        arrival = 0;
        burst = new int[10];      // There will only ever be a maximum of 10 bursts
        numOfPreemptions = 0;
    }


    /*
    * toString
    * Prints the contents of each priority queue
    *
    * @return String: String of the priority queues and their contents
    */
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
