import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {

    private class Node {
        Item item;
        Node next;
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (current == null) {
                throw new java.util.NoSuchElementException();
            }
            Item res = current.item;
            current = current.next;
            return res;
        }
    }
    /**
     * pointer to the first node.
     */
    private Node first = null;
    /**
     * pointer to the last node.
     */
    private Node last = null;
    /**
     * number of nodes in the deque.
     */
    private int size = 0;

 // construct an empty deque
    public Deque(){
    }
/**
 * check if the deque empty?
 * @return true if no elements in the deque, false otherwise
 */
    public final boolean isEmpty() {
        return first == null;
    }
/**
 * return the number of items on the deque.
 * @return  number of items currently in the deque
 */
    public final int size() {
        return size;
    }
/**
 * insert the item at the front.
 * @param item Item to be added
 */
    public final void addFirst(final Item item) {
        if (item == null) {
            throw new java.lang.NullPointerException();
        }
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.next = oldfirst;
        size++;
        if (size == 1) {
            last = first;
        }
                
    }

    public final void addLast(final Item item) // insert the item at the end
    {
        if (item == null) {
            throw new java.lang.NullPointerException();
        }
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        oldLast.next = last;
        size++;
        if (size == 1) {
            first = last;
        }
    }

    public final Item removeFirst() // delete and return the item at the front
    {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        Item res = first.item;
        first = first.next;
        size--;
        return res;
    }

    public final Item removeLast() // delete and return the item at the end
    {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        Item res = last.item;
        last = null;
        size--;
        if (size==1) {
            first = last;
        }
        return res;

    }
/**
 * return an iterator over items in order
 * from front to end.
 * @return instance of ListIteraor class
 */
    public final Iterator<Item> iterator() {
        return new ListIterator();
    }

}
