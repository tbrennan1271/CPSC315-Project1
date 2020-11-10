/*
* process.java
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
    public int numOfPreemptions;
    public int turnaroundTime;
    public int totalReadyQueueWaitingTime;

    /*
    * process
    * Initializes the global variables and the priority queues to be used
    * 
    * @param char id: The single character id
    * @param String priority: Either HP or LP
    * @param int arrival: Initial arrival time of the process
    * @param int[] burst: Array storing the burst lengths and blocked lengths (alternating)
    */
    public process(char id, String priority, int arrival, int[] burst){
        this.id = id;
        this.priority = priority;
        this.arrival = arrival;
        this.burst = burst;      // There will only ever be a maximum of 10 bursts
        index = 0;
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
