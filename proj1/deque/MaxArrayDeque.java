package deque;
import java.util.Comparator;


// As an attempt to become a more proficient software engineer I have tried my best to explain the process of each line of code.
public class MaxArrayDeque<T> extends ArrayDeque<T> {
    // The Comparator<T> given to the MaxArrayDeque in the constructor
    private final Comparator<T> comparator;

    public MaxArrayDeque(Comparator<T> c) {
        this.comparator = c;
    }

    public T max() {
        T max = null;
        for (int i = 0; i < this.size(); i++) {
            T item = this.get(i);
            if (comparator.compare(item, max) > 0) {
                max = item;
            }
        }
        return max;
    }

    public T max(Comparator<T> comp) {
        if (this.isEmpty()) {
            return null;
        }
        T max = this.get(0);
        for (int i = 0; i < this.size(); i++) {
            T current = this.get(i);
            if (comp.compare(current, max) > 0) {
                max = current;
            }
        }
        return max;
    }}
