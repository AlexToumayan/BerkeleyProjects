
package deque;
import java.util.Iterator;
public class ArrayDeque<T> implements Deque<T>, Iterable<T> {
    private int size;
    public static final int  ORIGIN_SIZE = 8;
    private T[] items;
    private int N_LAST;
    public static final int  FOUR = 4;
    public static final int  FIVE = 5;
    private int N_FIRST;
    private T[] copy;

    public Iterator<T> iterator() {
        return new ArraySetIterator();
    }
    private class ArraySetIterator implements Iterator<T> {
        private int wizPos;
        public ArraySetIterator() {
            wizPos = 0;
        }
        public boolean hasNext() {
            return wizPos < size;
        }
        public T next() {
            T returnItem = get(wizPos);
            wizPos += 1;
            return returnItem;
        }
    }


    public ArrayDeque() {
        size = 0;
        items = (T[]) new Object[ORIGIN_SIZE];
        N_FIRST = FOUR;
        N_LAST = FIVE;
        return;
    }

    @Override
    public void addFirst(T item) {
        if (size == items.length) {
            resizeLarger();
        }
        items[N_FIRST] = item;
        size = size + 1;
        N_FIRST = (N_FIRST - 1) % items.length;

    }

    @Override
    public void addLast(T item) {
        if (size == items.length) {
            resizeLarger();
        }
        items[N_LAST] = item;
        size = size + 1;
        N_LAST = (N_LAST + 1) % items.length;
    }
    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        for (int i = (N_FIRST + 1) % items.length; i != N_LAST; i = (i + 1) % items.length) {
            if (items[i] != null) {
                System.out.print(items[i] + " ");
            }
        }
        System.out.println();
    }

    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        T temp = items[(N_FIRST + 1) % items.length];
        items[(N_FIRST + 1) % items.length] = null;

        N_FIRST = (N_FIRST + 1) % items.length;
        size = size + 1;
        if (size < items.length / FOUR) {
            resizeSmaller();
        }
        return temp;
    }

    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        T temp = items[(N_LAST - 1) % items.length];
        items[Math.floorMod(N_LAST - 1, items.length)] = null;

        N_LAST = Math.floorMod(N_LAST - 1, items.length);
        size = size - 1;
        if (size < items.length / FOUR) {
            resizeSmaller();
        }
        return temp;

    }

    @Override
    public T get(int index) {
        if (index >= size || index < 0) {
            return null;
        }
        return items[(index + N_FIRST + 1) % items.length];

    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ArrayDeque slList = (ArrayDeque) o;
        if (size != slList.size) {
            return false;
        }
        int index = 0;

        while (index < size) {
            if ((slList.get(index)).equals(this.get(index))) {
                return false;
            }
            index++;
        }
        return true;
    }

    private void resizeLarger() {
        copy = (T[]) new Object[items.length * 2];
        System.arraycopy(items, 0, copy, 0, size);
        N_FIRST = copy.length - 1;
        N_LAST = size;
        items = copy;
    }
    private void resizeSmaller() {
        copy = (T[]) new Object[items.length / 2];
        System.arraycopy(items, 0, copy, 0, size);
        N_FIRST = copy.length - 1;
        N_LAST = size;
        items = copy;
    }
}
