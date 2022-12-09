package deque;

public class ArrayDeque<T> implements Deque<T> {
    private int size;
    private T[] items;
    private int nextLast;
    private int nextFirst;
    private T[] copy;

    public ArrayDeque() {
        size = 0;
        items = (T[]) new Object[8];
        nextFirst = 4;
        nextLast = 5;
        return;
    }

    @Override
    public void addFirst(T item) {
        if (size==items.length) {
            resizeLarger();
        }
        items[nextFirst] = item;
        size = size + 1;
        nextFirst = (nextFirst - 1) % items.length;

    }

    @Override
    public void addLast(T item) {
        if (size==items.length) {
            resizeLarger();
        }
        items[nextLast] = item;
        size = size + 1;
        nextLast = (nextLast + 1) % items.length;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        for (int i = (nextFirst + 1) % items.length; i != nextLast; i = (i + 1) % items.length) {
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
        T temp = items[(nextFirst + 1) % items.length];
        items[(nextFirst + 1) % items.length] = null;

        nextFirst = (nextFirst + 1) % items.length;
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
        T temp = items[(nextLast - 1)%items.length];
        items[Math.floorMod(nextLast - 1, items.length)] = null;

        nextLast = Math.floorMod(nextLast - 1, items.length);
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
        return items[(index + nextFirst + 1) % items.length];

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
        nextFirst = copy.length - 1;
        nextLast = size;
        items = copy;
    }
    private void resizeSmaller(){
        copy=(T[]) new Object[items.length/2];
        System.arraycopy(items,0,copy,0,size);
        nextFirst = copy.length - 1;
        nextLast = size;
        items = copy;
    }
}