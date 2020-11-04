/*
* lowPriority
* Used to maintain the priority queues
*
* @author Gabby Rogers
* @author Tyler Brennan
*/

import java.util.*;

public class lowPriority {
    /* GLOBAL VARIABLES */
    private Deque<process> H;
    private Deque<process> L2;
    private Deque<process> L3;
    private Deque<process> blocked;     // Potentially shouldn't be here (or be a dequeue)

    public final int NUM_PREEMPT = 3;
    public int L2Quant;
    public int L3Quant;

    /*
    * lowPriority
    * Initializes the global variables and the priority queues to be used
    */
    public lowPriority(int L2Quant, int L3Quant){
        H = new LinkedList<>();
        L2 = new LinkedList<>();
        L3 = new LinkedList<>();
        blocked = new LinkedList<>();
        this.L2Quant = L2Quant;
        this.L3Quant = L3Quant;
    }

    /*
    * readyProcess
    * Takes processes as input and assigns them to the proper priority queue
    *
    * @param process p: The process to be assigned to a priority queue
    */
    public void readyProcess(process p){
        if(p.priority.equals("HP")){
            H.addLast(p);
        } else if(p.blockCount <= NUM_PREEMPT){
            L2.addLast(p);
        } else{
            L3.addLast(p);
        }
    }

    /*
    * unblocked
    * Checks if a job is ready to be placed back on a priority queue
    *
    * @param int clock: Overall clock time of the system to check if job should be unblocked
    */
    public void unblocked(int clock){
        process p = blocked.getFirst();
        if(clock == p.burst[p.index] + p.arrival){
            p.blockCount++;
            readyProcess(blocked.removeFirst());
        }
    }

    /*
    * toString
    * Prints the contents of each priority queue
    *
    * @return String: String of the priority queues and their contents
    */
    public String toString(){
        String res = "";
        process first;
        process curr;

        if(!H.isEmpty()){
            first = H.getFirst();
            curr = H.getFirst();
            res += "\nHIGH PRIORITY:\n-----------------------------\n";
            res += first;
            H.addLast(curr);
            H.removeFirst();
            curr = H.getFirst();
            while(curr != first){
                res += "\n" + curr;
                H.addLast(curr);
                H.removeFirst();
                curr = H.getFirst();
            }
            res += "\n";
        }
        if(!L2.isEmpty()){
            first = L2.getFirst();
            curr = L2.getFirst();
            res += "\nLOW PRIORITY 2:\n-----------------------------\n";
            res += first;
            L2.addLast(curr);
            L2.removeFirst();
            curr = L2.getFirst();
            while(curr != first){
                res += "\n" + curr;
                L2.addLast(curr);
                L2.removeFirst();
                curr = L2.getFirst();
            }
        }
        if(!L3.isEmpty()){
            first = L3.getFirst();
            curr = L3.getFirst();
            res += "\nLOW PRIORITY 3:\n-----------------------------";
            res += first + "\n";
            L3.addLast(curr);
            L3.removeFirst();
            curr = L3.getFirst();
            while(curr != first){
                res += "\n" + curr;
                L3.addLast(curr);
                L3.removeFirst();
                curr = L3.getFirst();
            }
        }
        return res;
    }
}
