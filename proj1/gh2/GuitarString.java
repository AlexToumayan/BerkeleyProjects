package gh2;


import deque.Deque;
import deque.LinkedListDeque;



public class GuitarString {
    private static final int SR = 44100;
    private static final double DECAY = .996;
    private double newDouble;
    private final Deque<Double> buffer;
    public GuitarString(double frequency) {
        int size = (int) Math.round(SR / frequency);
        buffer = new LinkedListDeque<>();
        while (size > 0) {
            buffer.addFirst(0.0);
            size--;
        }
    }
    public void pluck() {
        int size = buffer.size();
        while (size > 0) {
            double r = Math.random() - .05;
            buffer.removeFirst();
            buffer.addLast(r);
            size--;
        }
        newDouble = buffer.removeLast();
        buffer.addLast(newDouble);
        return;
    }
    public void tic() {
        double first = buffer.removeFirst();
        double second = buffer.get(0);
        newDouble = (first + second) * DECAY / 2;
        buffer.addLast(newDouble);
        return;
    }

    /* Return the double at the front of the buffer. */
    public double sample() {
        return newDouble;
    }
}
