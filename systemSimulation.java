/*
* systemSimulation
* Used to read in input and to hold the main code
*
* @author Gabby Rogers
* @author Tyler Brennan
*/

import java.io.File;
import java.io.FileInputStream;
import java.util.*;

public class systemSimulation{
    /* GLOBAL VARIABLES */
    public static ArrayList<process> processInput = new ArrayList<process>(); // List of processes saved as a LinkedList because we do not know the maximum number of processes
    public static priority low;
    private static int l2Quant;
    private static int l3Quant;

    /*
    * input
    * Input code parses the input file and breaks it into the corresponding sections of different processes
    * @param file - The file name pulled from the command line (file should be saved in the same folder as the code)
    */
    private static void input(String file){
        try{
            Scanner reader = new Scanner(new FileInputStream(file));
            process current = new process();  // Creates new processes to append to processInput
            low = new priority(reader.nextInt(), reader.nextInt());  // Set quantums for each low priority queue
            /*l2Quant = reader.nextInt();       // The quantums for each low priority queue are always found at the start of the input file
            l3Quant = reader.nextInt();*/
            int count = 0;                    // Keeps track of position in the input file, as they are all organized the same way
            int tempInt;
            while(reader.hasNext()){
                if (count > 2){                 // Records the burst length/blocked time and checks if the process has ended
                    tempInt = reader.nextInt();
                    if(tempInt != -1)
                        current.burst[count - 3] = tempInt;
                    else {
                        count = -1;
                        processInput.add(current);
                        current = new process();
                    }
                } else if(count == 2)           // Records the arrival time of the process
                    current.arrival = reader.nextInt();
                else if(count == 1)             // Records the priority
                    current.priority = reader.next();
                else if(count == 0)             // Records the ID
                    current.id = reader.next().charAt(0);
                count ++;
            }
        }
        catch(Exception e){
            System.out.println("Error: Input a proper file");
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

    /* TEMP MAIN */
    public static void main(String[] args){
        final int CLOCK_MAX = 80;
        int clock = 0;

        // first check to see if the program was run with the command line argument
        if(args.length < 1) {
            System.out.println("Error: java ClassName inputfile");
            System.exit(1);
        } else {
            input(args[0]);
        }

        for(int i = 0; i < processInput.size(); i++){
            System.out.println(processInput.get(i));
        }

        /*
        Loop
        	clock = clock + 1;		// advance the time
        	Check_new_jobs;		    // check if new jobs are entering the system
        	Block_job_return_check;	// check if job on is returning from being blocked
        	Cpu_job_done_check;		// check if current running job terminates/blocks
        	Quantum_check;		    // check if current running job’s quantum expired
        	Cpu_check;			    // check if CPU idle and if so pick job to run
        */
        for(int i = 0; i < processInput.size(); i++){
            low.readyProcess(processInput.get(i));
        }

        System.out.println(low);
        for(clock = 0; clock < CLOCK_MAX; clock++){
            
        }
    }
}
