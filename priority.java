/*
* priority.java
* Used to maintain the priority queues
*
* @author Gabby Rogers
* @author Tyler Brennan
*/

import java.util.*;

public class priority{
    /* GLOBAL VARIABLES */
    public final int MAX_PREEMPTIONS = 3;

    private Deque<process> H;
    private Deque<process> L2;
    private Deque<process> L3;
    private Map<Integer, process> blocked;

    /*
    * priority
    * Initializes the global variables and the priority queues to be used
    *
    * @param int l2Quant: The quantum of the higher low priority queue
    * @param int l3Quant: The quantum of the lowest priority queue
    */
    public priority(){//int L2Quant, int L3Quant){
        H = new LinkedList<>();
        L2 = new LinkedList<>();
        L3 = new LinkedList<>();
        blocked = new HashMap<Integer, process>();
        //this.L2Quant = L2Quant;
        //this.L3Quant = L3Quant;
    }

    /*
    * readyProcess
    * Takes processes as input and assigns them to the proper priority queue
    *
    * @param process p: The process to be assigned to a priority queue
    */
    public void readyProcess(process p){

        if(p.priority.equals("HP") && p != null){
            H.addLast(p);
        } else if(p.numOfPreemptions <= MAX_PREEMPTIONS && p != null){
            L2.addLast(p);
        } else if (p.numOfPreemptions > MAX_PREEMPTIONS && p != null) {
            L3.addLast(p);
        }
    }

    /*
    * pickProcess
    * Takes processes as input and returns them to be run on the CPU
    *
    * @return process: The process to be run on the CPU or null if there are no processes available
    */
    public process pickProcess(){
        if (H.isEmpty() == false){
            return H.removeFirst();
        } else if (L2.isEmpty() == false){
            return L2.removeFirst();
        } else if (L3.isEmpty() == false){
            return L3.removeFirst();
        }
        return null;
    }

    /*
    * unblocked
    * Checks if a job is ready to be placed back on a priority queue
    *
    * @param int clock: Overall clock time of the system to check if job should be unblocked
    */
    public void unblocked(int clock){
        process p = blocked.get(clock);
        if(clock == p.burst[p.index] + p.arrival){
            p.numOfPreemptions++;
            readyProcess(blocked.remove(clock));
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
