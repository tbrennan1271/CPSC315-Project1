import java.util.*;

public class lowPriority {
    public char id;
    public int arrival;
    public int[] burst;
    
    public lowPriority(int id, int arrival, int[] burst){
        id = ' ';
        arrival = 0;
        burst = new int[10];
    }

    Queue<Integer> queueL2 = new LinkedList<>();
    Queue<Integer> queueL3 = new LinkedList<>();

}