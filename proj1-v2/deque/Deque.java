package deque;

/* The Deque interface defines the expected behavior for any
* Deque, whether it is an ArrayDeque or LinkedListDeque. A
* Deque is a doubly-ended queue, that allows you to quickly add
* and remove items from the front and back. */
public interface Deque<T> {
    public void addFirst(T item);
    public void addLast(T item);
    public boolean isEmpty();
    public int size();
    public void printDeque();
    public T removeFirst();
    public T removeLast();
    public T get(int index);
    public boolean equals(Object o);

}
