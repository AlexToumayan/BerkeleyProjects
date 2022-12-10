
package deque;
import java.util.Comparator;
// @ educative.io as you can see from my 10+ prior git uploads I tried this problem several
// times on my own, after which I searched the internet for problems in my solution.
// I want to disclose since we are meant to @ any sites that help us in problem-solving
// that I was walked through the errors in my max class and Comparator from an outside source.
// I did not however, look at a definitive answer via git or other proxies.
public class MaxArrayDeque<T> extends ArrayDeque<T> {
    private Comparator<T> comparison;

    public MaxArrayDeque(Comparator<T> c) {
        super();
        comparison = c;
    }

    public T max() {
        if (isEmpty()) {
            return null;
        }
        T largestInteger = (T) get(0);
        for (int i = 0; i < size(); i++) {
            if (comparison.compare((T) get(i), largestInteger) > 0) {
                largestInteger = (T) get(i);
            }
        }
        return largestInteger;
    }

    public T max(Comparator<T> c) {
        if (isEmpty()) {
            return null;
        }
        T largestInteger = (T) get(0);
        for (int i = 0; i < size(); i++) {
            if (c.compare((T) get(i), largestInteger) > 0) {
                largestInteger = (T) get(i);
            }
        }
        return largestInteger;
    }
}


