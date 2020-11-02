import java.util.*;

public class highPriority{
    public char id;
    public int arrival;
    public int[] burst;

    Queue<Integer> queueH2 = new LinkedList<>();

    public highPriority(int id, int arrival, int[] burst){
        id = ' ';
        arrival = 0;
        burst = new int[10];
    }


}