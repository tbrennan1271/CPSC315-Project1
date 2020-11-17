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
        int clock = -1;

        // first check to see if the program was run with the command line argument
        if(args.length < 1) {
            System.out.println("Error: java ClassName inputfile");
            System.exit(1);
        } else {
            cpu = input.readFile(args[0], processInput);
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


        List<process> index;
        process current;


        while(!blockedProc.isEmpty() || !readyQueues.isEmpty() || !cpu.isIdle() || !processInput.isEmpty()){
            // advance the time
            clock++;
            current = null;

            // check if new jobs are entering the system
            index = input.checkNewJobs(clock, processInput);
            for(int i = 0; i < index.size(); i++){
                readyQueues.readyProcess(index.get(i), clock);
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
            if(!blockedProc.isEmpty() || !readyQueues.isEmpty() || !cpu.isIdle() || !processInput.isEmpty())
                /*System.out.println(!blockedProc.isEmpty());
                System.out.println(!readyQueues.isEmpty());
                System.out.println(!cpu.isIdle());
                System.out.println(!processInput.isEmpty());
                System.out.println(cpu.running);
                System.out.println(cpu);*/
                cpu.gantt(clock);
        }
        avgTotalWait /= completedProc.size();
        avgTurnaround /= completedProc.size();
        double cpuUtil = (double)(cpu.utilization * 100) / cpu.totalTime;

        // Output

        System.out.println("CPU Utilization: " + cpu.utilization + "/" + cpu.totalTime + " = " + cpuUtil + "%");
        System.out.println("Throughput: " + completedProc.size() + " jobs/" + cpu.totalTime + " units");
        System.out.println("Average ready queue wait time: " + avgTotalWait);
        System.out.println("Average turnaround time: " + avgTurnaround);

        for(int i = 0; i < completedProc.size(); i++){
            System.out.print("\nReady queue wait time for process " + completedProc.get(i).id + ": ");
            System.out.println(completedProc.get(i).totalReadyQueueWaitingTime);
            System.out.print("Turnaround time for process " + completedProc.get(i).id + ": ");
            System.out.println(completedProc.get(i).turnaroundTime);
            System.out.println();
        }

        System.out.println(cpu);
    }
// blocked map, arrayList, CPU, priority queues
    public boolean checkProcesses(Blocked b, CPU cpu, Priority priority){
        if (b.isEmpty() && cpu.isIdle() && priority.isEmpty()){
            return true;
        }
        return false;
    }
}
