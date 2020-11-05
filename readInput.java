/*
* readInput
* Used to read in input and to hold the main code
*
* @author Gabby Rogers
* @author Tyler Brennan
*/

import java.io.File;
import java.io.FileInputStream;
import java.util.*;

public class readInput{
    private static final int BURST_LEN = 10;
    /*
    * readFile
    * Input code parses the input file and breaks it into the corresponding sections of different processes
    *
    * @param file - The file name pulled from the command line (file should be saved in the same folder as the code)
    */
    public static priority readFile(String file, ArrayList<process> processInput){
        /* VARIABLES */
        priority readyQueues;
        Scanner reader;
        process current;    // Creates new processes to append to processInput
        int l2Quant;
        int l3Quant;
        char id;
        String priority;
        int arrival;
        int[] burst;

        int count = 0;      // Keeps track of position in the input file, as they are all organized the same way
        int tempInt;        // Maintains the burst/blocked value to save in burst[]

        try{
            reader = new Scanner(new FileInputStream(file));
            burst = new int[BURST_LEN];
            id = ' ';
            priority = "";
            arrival = 0;

            l2Quant = reader.nextInt();
            l3Quant = reader.nextInt();
            readyQueues = new priority(l2Quant, l3Quant);  // Set quantums for each low priority queue
            while(reader.hasNext()){
                if (count > 2){                 // Records the burst length/blocked time and checks if the process has ended
                    tempInt = reader.nextInt();
                    if(tempInt != -1)
                        burst[count - 3] = tempInt;
                    else {
                        count = -1;
                        current = new process(id, priority, arrival, burst);
                        burst = new int[BURST_LEN];
                        processInput.add(current);
                    }
                } else if(count == 2)           // Records the arrival time of the process
                    arrival = reader.nextInt();
                else if(count == 1)             // Records the priority
                    priority = reader.next();
                else if(count == 0)             // Records the ID
                    id = reader.next().charAt(0);
                count ++;
            }
            return readyQueues;
        }
        catch(Exception e){
            System.out.println("Error: Input a proper file");
            return null;
        }
    }

    public int checkNewJobs(int currentClockTick, ArrayList<process> processInput){
        for (int i = 0; i < processInput.size(); i++){
            if (processInput.get(i).arrival == currentClockTick){
                return i;
            }
        }
        return -1;
    }
}
