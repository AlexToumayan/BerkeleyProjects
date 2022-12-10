package deque;
import java.util.Comparator;
import java.util.Iterator;
public class ArrayDeque<T> implements Deque<T>,Iterable<T> {
    private int size;
    private T[] items;
    private int n_last;
    private int n_first;
    private T[] copy;

    public Iterator<T> iterator(){
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
        items = (T[]) new Object[8];
        n_first = 4;
        n_last = 5;
        return;
    }

    @Override
    public void addFirst(T item) {
        if (size==items.length) {
            resizeLarger();
        }
        items[n_first] = item;
        size = size + 1;
        n_first = (n_first - 1) % items.length;

    }

    @Override
    public void addLast(T item) {
        if (size==items.length) {
            resizeLarger();
        }
        items[n_last] = item;
        size = size + 1;
        n_last = (n_last + 1) % items.length;
    }
    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        for (int i = (n_first + 1) % items.length; i != n_last; i = (i + 1) % items.length) {
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
        T temp = items[(n_first + 1) % items.length];
        items[(n_first + 1) % items.length] = null;

        n_first = (n_first + 1) % items.length;
        size = size + 1;
        if (size<items.length/4) {
            resizeSmaller();
        }
        return temp;
    }

    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        T temp = items[(n_last - 1)%items.length];
        items[Math.floorMod(n_last - 1, items.length)] = null;

        n_last = Math.floorMod(n_last - 1, items.length);
        size = size - 1;
        if (size<items.length/4) {
            resizeSmaller();
        }
        return temp;

    }

    @Override
    public T get(int index) {
        if (index >= size || index < 0) {
            return null;
        }
        return items[(index + n_first + 1) % items.length];

    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ArrayDeque slList = (ArrayDeque) o;
        if (size != slList.size) return false;
        int index=0;

        while (index<size) {
            if ((slList.get(index)).equals(this.get(index))) {
                return false;
            }
            index++;
        }
        return true;
    }

    private void resizeLarger() {
        copy=(T[]) new Object[items.length*2];
        System.arraycopy(items,0,copy,0,size);
        n_first = copy.length - 1;
        n_last = size;
        items = copy;
    }
    private void resizeSmaller(){
        copy=(T[]) new Object[items.length/2];
        System.arraycopy(items,0,copy,0,size);
        n_first = copy.length - 1;
        n_last = size;
        items = copy;
    }
}