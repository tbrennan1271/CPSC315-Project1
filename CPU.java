/*
* CPU.java
* Maintains the CPU and keeps track of the running processes
*
* @author Gabby Rogers
* @author Tyler Brennan
*/

public class CPU{
    private final int BURST_LEN = 10;
    private final int GANTT_LENGTH = 80;
    public final int MAX_PREEMPTIONS = 3;
    private char[] gantt;
    public process running;
    private int endTime;
    private int startTime;

    public int l2Quant;
    public int l3Quant;

    /*
    * CPU
    * Initializes the CPU class
    */
    public CPU(int l2Quant, int l3Quant){
        gantt = new char[GANTT_LENGTH];
        running = null;
        endTime = -1;
        startTime = -1;
        this.l2Quant = l2Quant;
        this.l3Quant = l3Quant;

    }

    /*
    * runProcess
    * Initializes the global variables and the priority queues to be used
    *
    * @param process p: The process to be run
    * @param int clock: Current clock time in order to calculate the proper end time
    */
    public void runProcess(process p, int clock){
        running = p;
        endTime = p.burst[p.index] + clock;
        startTime = clock;
    }

    /*
    * cpuJobDoneCheck
    * Checks if the current process has completed its entire burst
    *
    * @param int clock: Current clock time in order to calculate the proper end time
    * @return process: The process that has finished running or null if the process is
    *       still running/there is no currently running process
    */
    public process cpuJobDoneCheck(int clock){
        process tempProc = null;
        if(clock == endTime && running != null){
            endTime = -1;
            startTime = -1;
            tempProc = running;
            tempProc.index++;
            running = null;
        }
        return tempProc;
    }

    /*
    * quantumCheck
    * Checks if a low priority job has finished an entire quantum
    *
    * @param int clock: Current clock time in order to calculate the proper end time
    * @return process: The process that has been preempted or null if the process is
    *       still running/there is no currently running process
    */
    public process quantumCheck(int clock){
        process tempProc = null;
        int quantum = 0;
        if(running != null){
            if(running.priority.equals("LP") && running.numOfPreemptions <= MAX_PREEMPTIONS){
                quantum = l2Quant;
            } else if(running.priority.equals("LP") && running.numOfPreemptions > MAX_PREEMPTIONS){
                quantum = l3Quant;
            }
            if(quantum + startTime == clock && quantum != 0){
                System.out.print("----- ");
                System.out.print(running.id);
                System.out.println(" USED ENTIRE QUANTUM -----");
                running.numOfPreemptions++;
                tempProc = running;
                tempProc.burst[tempProc.index] -= quantum;
                endTime = -1;
                running = null;
            }
        }
        return tempProc;
    }

    /*
    * isIdle
    * Checks if the CPU has a currently running process
    *
    * @return boolean: True if there is no running process and false if there is
    */
    public boolean isIdle(){
        if(running == null)
            return true;
        else
            return false;
    }

    private void appendGantt(int startTime, int endTime){

    }
}
