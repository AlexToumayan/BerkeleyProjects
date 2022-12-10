package deque;
import java.util.Iterator;
public class LinkedListDeque<T> implements Deque<T>, Iterable<T> {
    public Iterator<T> iterator() {
        return new LinkedListIterator();
    }
    private class LinkedListIterator implements Iterator<T> {
        private int wizPos;
        public LinkedListIterator() {
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

    public class Node {
        private T item;
        public Node next;
        public Node last;

        private Node(T item, Node next, Node last) {
            this.item = item;
            this.next = next;
            this.last = last;
        }
    }

    private final Node sentinel;
    private int size;

    public LinkedListDeque() {
        sentinel = new Node(null, null, null);
        sentinel.next = sentinel;
        sentinel.last = sentinel;
        size = 0;
    }


    @Override
    public void addFirst(T item) {
        if (size == 0) {
            sentinel.next = new Node(item, sentinel, sentinel);
            sentinel.last = sentinel.next;
        } else {
            Node added = new Node(item, sentinel.next, sentinel);
            sentinel.next.last = added;
            sentinel.next = added;
        }
        size += 1;
        return;
    }

    @Override
    public void addLast(T item) {
        if (size == 0) {
            sentinel.next = new Node(item, sentinel, sentinel);
            sentinel.last = sentinel.next;
        } else {
            Node added = new Node(item, sentinel, sentinel.last);
            sentinel.last.next = added;
            sentinel.last = added;
        }
        size += 1;
        return;
    }


    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void printDeque() {
        Node curr = sentinel.next;
        while (curr != sentinel) {
            System.out.print(curr.item);
            if (curr.next != sentinel) {
                System.out.print(" ");
            }
            curr = curr.next;
        }
        System.out.println();
        return;
    }

    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        Node remove = sentinel.next;
        sentinel.next = remove.next;
        (sentinel.next).last = sentinel;
        size -= 1;
        return remove.item;
    }

    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        Node remove = sentinel.last;
        sentinel.last = remove.last;
        (remove.last).next = sentinel;
        size -= 1;
        return remove.item;
    }

    @Override
    public T get(int index) {
        if (index >= size) {
            return null;
        }
        Node p = sentinel.next;
        while (index > 0) {
            p = p.next;
            index -= 1;
        }
        return p.item;
    }

    public T getRecursive(int index) {
        if (index >= size) {
            return null;
        }
        LinkedListDeque copy = this;
        return recursiveHelper(copy, index).item;
    }

    private Node recursiveHelper(LinkedListDeque list, int index) {
        if (index == 0) {
            return list.sentinel.next;
        }
        list.removeFirst();
        return recursiveHelper(list, index - 1);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LinkedListDeque slList = (LinkedListDeque) o;
        if (size != slList.size) {
            return false;
        }

        int index=0;

        while (index<size) {
            if ((slList.get(index)).equals(this.get(index))) {
                return false;
            }
            index++;
        }
        return true;
    }
}
