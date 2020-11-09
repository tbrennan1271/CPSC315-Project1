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
     * blocked
     * Initializes the global variables to be used
     *
     * @param p - process being blocked
     * @param blockedTime - amt of time process is blocked
     * @return
     */
    public process addToBlocked(process p, int blockedTime){
        process tempProc = null;
        blocked.put(blockedTime + p.burst[p.index], p);
        return tempProc;
    }

    /**
     * blockedJobReturnCheck
     * Checks to see if a processes blocked end time lines up with the current clock
     *
     * @param clock
     */
    public process blockedJobReturnCheck(int clock){
        process p = null;
        if(blocked.contsinsKey(clock)){
            p.index++;
            p = blocked.get(clock);
            //tempPriority.readyProcess(blocked.remove(clock));
        }
        return p;
    }

}
