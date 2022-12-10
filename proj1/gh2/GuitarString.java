package gh2;

// TODO: uncomment the following import once you're ready to start this portion
import deque.Deque;
import deque.LinkedListDeque;
// TODO: maybe more imports

//Note: This file will not compile until you complete the Deque implementations
public class GuitarString {
    /** Constants. Do not change. In case you're curious, the keyword final
     * means the values cannot be changed at runtime. We'll discuss this and
     * other topics in lecture on Friday. */
    private static final int SR = 44100;
    private static final double DECAY = .996;
    private double newDouble;

    private final Deque<Double> buffer;

    /* Create a guitar string of the given frequency.  */
    public GuitarString(double frequency) {
        int size=(int) Math.round(SR/frequency);
        buffer=new LinkedListDeque<>();
        while (size>0) {
            buffer.addFirst(0.0);
            size--;
        }
    }


    /* Pluck the guitar string by replacing the buffer with white noise. */
    public void pluck() {
        int size= buffer.size();
        while (size>0) {
            double r=Math.random()-.05;
            buffer.removeFirst();
            buffer.addLast(r);
            size--;
        }
        newDouble=buffer.removeLast();
        buffer.addLast(newDouble);
        return;
    }

    /* Advance the simulation one time step by performing one iteration of
     * the Karplus-Strong algorithm.
     */
    public void tic() {
        double first=buffer.removeFirst();
        double second= buffer.get(0);
        newDouble=(first+second)*DECAY/2;
        buffer.addLast(newDouble);
        return;
    }

    /* Return the double at the front of the buffer. */
    public double sample() {
        return newDouble;

    }
}
