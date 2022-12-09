package deque;
import java.util.Collection;
import java.util.Comparator;
import java.util.Collections;
import java.util.Iterator;
// As an attempt to become a more proficient software engineer I have tried my best to explain the process of each line of code.
public class MaxArrayDeque<T> extends ArrayDeque<T> implements Collection<T> {
    // The Comparator<T> given to the MaxArrayDeque in the constructor
    private final Comparator<T> comparator;

    public MaxArrayDeque(Comparator<T> c) {
        super(c);
        this.comparator = c;
    }

    // Returns the maximum element in the deque as determined by the Comparator<T> given in the constructor
    public T max() {
        // If the deque is empty, return null
        if (this.isEmpty()) {
            return null;
        }

        // Otherwise, return the maximum element in the deque as determined by the Comparator<T>
        return Collections.max(this, comparator);
    }

    // Returns the maximum element in the deque as determined by the Comparator<T> parameter
    public T max(Comparator<T> c) {
        // If the deque is empty, return null
        if (this.isEmpty()) {
            return null;
        }

        // Otherwise, return the maximum element in the deque as determined by the Comparator<T> parameter
        return Collections.max(this, c);
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return null;
    }

    @Override
    public boolean add(T t) {
        return false;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }
}
