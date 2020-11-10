/*
* CPU.java
* Maintains the CPU and keeps track of the running processes
*
* @author Gabby Rogers
* @author Tyler Brennan
*/

public class CPU{
    //private final int BURST_LEN = 10;
    private final int GANTT_LENGTH = 80;
    private final int MAX_PREEMPTIONS = 3;
    private final int TICK_INTERVAL = 5;
    private char[] gantt;
    public process running;
    private int endTime;
    private int startTime;
    public int l2Quant;
    public int l3Quant;
    Blocked blockedProc = new Blocked();

    /*
    * CPU
    * Initializes the CPU class
    *
    * @param int l2Quant: The quantum of the higher low priority queue
    * @param int l3Quant: The quantum of the lowest priority queue
    */
    public CPU(int l2Quant, int l3Quant){
        gantt = new char[GANTT_LENGTH + (GANTT_LENGTH / 5)];    // The (GANTT_LENGTH / 5) creates space for '|' every 5 units
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
    public process runProcess(process p, int clock){
        if(p != null){
            running = p;
            endTime = p.burst[p.index] + clock;
            startTime = clock;
            return running;
        }
        return null;
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
            tempProc.numOfPreemptions = 0;
            blockedProc.addToBlocked(running, tempProc.index);
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
            if(running.priority.equals("LP") && running.numOfPreemptions < MAX_PREEMPTIONS){
                quantum = l2Quant;
            } else if(running.priority.equals("LP") && running.numOfPreemptions >= MAX_PREEMPTIONS){
                quantum = l3Quant;
            }
            if(quantum + startTime == clock && quantum != 0){
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

    /**
     * gantt
     * Appends to the character array of process ID's for the gantt chart
     *
     * @param startTime - when process starts on CPU
     * @param endTime - when process gets off
     */
    public void gantt(int clock){
        if(clock % TICK_INTERVAL == 0 && clock != 0){
            gantt[clock +  (clock / TICK_INTERVAL) - 1] = '|';
        }
        if(isIdle()){
            gantt[clock + (clock / TICK_INTERVAL)] = '*';
        } else{
            gantt[clock + (clock / TICK_INTERVAL)] = running.id;
        }
    }

    public String toString(){
        String res = "";
        String temp = new String(gantt);
        gantt[gantt.length - 1] = ' ';
        res += temp.subSequence(0, (GANTT_LENGTH / 2) + ((GANTT_LENGTH / 2) / TICK_INTERVAL));
        if(gantt.length > (GANTT_LENGTH / 2) + ((GANTT_LENGTH / 2) / TICK_INTERVAL)){
            res += "\n";
            res += temp.subSequence((GANTT_LENGTH / 2) + ((GANTT_LENGTH / 2) / TICK_INTERVAL), gantt.length);
        }
        return res;
    }
}
