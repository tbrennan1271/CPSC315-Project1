/*
* main
* Maintains and runs all the necessary code
*
* @author Gabby Rogers
* @author Tyler Brennan
*/

import java.util.*;

public class main{
    public static void main(String[] args){
        /* VARIABLES */
        final int CLOCK_MAX = 80;

        ArrayList<process> processInput = new ArrayList<>();
        priority readyQueues = new priority(0, 0);
        readInput input = new readInput();
        int clock;

        // first check to see if the program was run with the command line argument
        if(args.length < 1) {
            System.out.println("Error: java ClassName inputfile");
            System.exit(1);
        } else {
            readyQueues = input.readFile(args[0], processInput);
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
            readyQueues.readyProcess(processInput.get(i));
        }

        System.out.println(readyQueues);
        for(clock = 0; clock < CLOCK_MAX; clock++){

        }
    }
}
