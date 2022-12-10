
package deque;
import java.util.Iterator;
public class ArrayDeque<T> implements Deque<T>, Iterable<T> {
    private int size;
    private T[] ITEMS;
    private int N_LAST;
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

        ITEMS = (T[]) new Object[8];
        N_FIRST = 4;
        N_LAST = 5;
        return;
    }

    @Override
    public void addFirst(T item) {
        if (size == ITEMS.length) {
            resizeLarger();
        }
        ITEMS[N_FIRST] = item;
        size = size + 1;
        N_FIRST = (N_FIRST - 1) % ITEMS.length;

    }

    @Override
    public void addLast(T item) {
        if (size == ITEMS.length) {
            resizeLarger();
        }
        ITEMS[N_LAST] = item;
        size = size + 1;
        N_LAST = (N_LAST + 1) % ITEMS.length;
    }
    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        for (int i = (N_FIRST + 1) % ITEMS.length; i != N_LAST; i = (i + 1) % ITEMS.length) {
            if (ITEMS[i] != null) {
                System.out.print(ITEMS[i] + " ");
            }
        }
        System.out.println();
    }

    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        T temp = ITEMS[(N_FIRST + 1) % ITEMS.length];
        ITEMS[(N_FIRST + 1) % ITEMS.length] = null;

        N_FIRST = (N_FIRST + 1) % ITEMS.length;
        size = size + 1;
        if (size < ITEMS.length / 4) {
            resizeSmaller();
        }
        return temp;
    }

    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        T temp = ITEMS[(N_LAST - 1) % ITEMS.length];
        ITEMS[Math.floorMod(N_LAST - 1, ITEMS.length)] = null;

        N_LAST = Math.floorMod(N_LAST - 1, ITEMS.length);
        size = size - 1;
        if (size < ITEMS.length / 4) {
            resizeSmaller();
        }
        return temp;

    }

    @Override
    public T get(int index) {
        if (index >= size || index < 0) {
            return null;
        }
        return ITEMS[(index + N_FIRST + 1) % ITEMS.length];

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
        copy = (T[]) new Object[ITEMS.length * 2];
        System.arraycopy(ITEMS, 0, copy, 0, size);
        N_FIRST = copy.length - 1;
        N_LAST = size;
        ITEMS = copy;
    }
    private void resizeSmaller() {
        copy = (T[]) new Object[ITEMS.length / 2];
        System.arraycopy(ITEMS, 0, copy, 0, size);
        N_FIRST = copy.length - 1;
        N_LAST = size;
        ITEMS = copy;
    }
}
