import java.util.*;
/*
* blocked class
* used to maintain processes that have been blocked upon completing a CPU cycle
*
* @author Gabby Rogers
* @author Tyler Brennan
*/

public class blocked {
    private Map<Integer, process> blocked;      // blocked processes and their blocked time                       

    priority tempPriority = new priority();

    public blocked(){
        blocked = new HashMap<Integer, process>();
    }

    /**
     * 
     * @param p - process being blocked
     * @param blockedTime - amt of time process is blocked
     * @return
     */
    public process addToBlocked(process p, int blockedTime){
        process tempProc = null;
        blocked.put(blockedTime, p);
        return tempProc;
    }

    /**
     * 
     * @param clock
     */
    public void blockedJobReturnCheck(int clock){
        process p = blocked.get(clock);
        if(clock == p.burst[p.index] + p.arrival){
            p.numOfPreemptions++;
            tempPriority.readyProcess(blocked.remove(clock));
        }
    
    }

}
