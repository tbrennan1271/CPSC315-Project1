/*
* Simulation.java
* Maintains and runs all the necessary code
*
* @author Gabby Rogers
* @author Tyler Brennan
*/

import java.util.*;

public class Simulation{
    public static void main(String[] args){
        /* VARIABLES */
        final int CLOCK_MAX = 80;
        double avgTurnaround = 0;
        double avgTotalWait = 0;

        ArrayList<process> processInput = new ArrayList<>();
        ArrayList<process> completedProc = new ArrayList<>();
        Priority readyQueues = new Priority();
        readInput input = new readInput();
        Blocked blockedProc = new Blocked();
        CPU cpu = new CPU(0, 0);
        int clock = 0;

        // first check to see if the program was run with the command line argument
        if(args.length < 1) {
            System.out.println("Error: java ClassName inputfile");
            System.exit(1);
        } else {
            cpu = input.readFile(args[0], processInput);
        }


        /*
        for(int i = 0; i < processInput.size(); i++){
            System.out.println(processInput.get(i));
        }*/


        /*
        for(int i = 0; i < processInput.size(); i++){
            readyQueues.readyProcess(processInput.get(i));
        }*/



        /*
        Loop
            clock = clock + 1;		// advance the time
            Check_new_jobs;		    // check if new jobs are entering the system
            Block_job_return_check;	// check if job on is returning from being blocked
            Cpu_job_done_check;		// check if current running job terminates/blocks
            Quantum_check;		    // check if current running job’s quantum expired
            Cpu_check;			    // check if CPU idle and if so pick job to run
        */

        //System.out.println(readyQueues);
        int index;
        process current;

        // advance the time
        for(clock = 0; clock < CLOCK_MAX; clock++){
            current = null;
            System.out.println(clock);

            // check if new jobs are entering the system
            index = input.checkNewJobs(clock, processInput);
            if(index >= 0){
                readyQueues.readyProcess(processInput.get(index), clock);
            }

            // check if job on is returning from being blocked
            current = blockedProc.blockedJobReturnCheck(clock);
            readyQueues.readyProcess(current, clock);

            // check if current running job terminates/blocks
            current = cpu.cpuJobDoneCheck(clock);
            if(current != null){
                if(current.burst[current.index] != 0){
                    blockedProc.addToBlocked(current, clock);
                } else{
                    current.turnaroundTime = clock - current.arrival;
                    completedProc.add(current);
                    avgTotalWait += current.totalReadyQueueWaitingTime;
                    avgTurnaround += current.turnaroundTime;
                    System.out.print("READY QUEUE WAIT TIME: ");
                    System.out.println(current.totalReadyQueueWaitingTime);
                    System.out.print("TURNAROUND: ");
                    System.out.println(current.turnaroundTime);
                }
            }

            // check if current running job’s quantum expired
            current = cpu.quantumCheck(clock);
            readyQueues.readyProcess(current, clock);

            // check if CPU idle and if so pick job to run
            if(cpu.isIdle()){
                current = readyQueues.pickProcess(clock);
                cpu.runProcess(current, clock);
            }
            cpu.gantt(clock);
            System.out.println(cpu.running);
            System.out.println();
        }
    avgTotalWait /= completedProc.size();
    avgTurnaround /= completedProc.size();
    System.out.print("AVERAGE READY QUEUE WAIT TIME: ");
    System.out.println(avgTotalWait);
    System.out.print("AVERAGE TURNAROUND: ");
    System.out.println(avgTurnaround);
    }
// blocked map, arrayList, CPU, priority queues
    public boolean checkProcesses(Blocked b, CPU cpu, Priority priority){
        if (b.isEmpty() && cpu.isIdle() && priority.isEmpty()){
            return true;
        }
        return false;
    }
}
