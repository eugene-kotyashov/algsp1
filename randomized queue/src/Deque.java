import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {

    private class Node {
        private Item item;
        private Node next;
        private Node prev;
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
    public Deque() {
    }

    /**
     * check if the deque empty?
     * 
     * @return true if no elements in the deque, false otherwise
     */
    public boolean isEmpty() {
        return (size == 0);
    }

    /**
     * return the number of items on the deque.
     * 
     * @return number of items currently in the deque
     */
    public int size() {
        return size;
    }

    /**
     * insert the item at the front.
     * 
     * @param item
     *            Item to be added
     */
    public void addFirst(Item item) {
        if (item == null) {
            throw new java.lang.NullPointerException();
        }
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.next = oldfirst;
        if (oldfirst != null) {
            oldfirst.prev = first;
        }
        size++;
        if (size == 1) {
            last = first;
        }
    }

    public void addLast(Item item) // insert the item at the end
    {
        if (item == null) {
            throw new java.lang.NullPointerException();
        }
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        if (oldLast != null) {
            oldLast.next = last;
        }
        last.prev = oldLast;
        size++;
        if (size == 1) {
            first = last;
        }
    }

    /**
     * delete and return the item at the front.
     * 
     * @return Item removed.
     */
    public Item removeFirst() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        Item res = first.item;
        first = first.next;
        if (first != null) {
            first.prev = null;
        }
        size--;
        return res;
    }

    /**
     * delete and return the item at the end.
     * 
     * @return Item removed
     */
    public Item removeLast() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        Item res = last.item;
        last = last.prev;
        if (last != null) {
            last.next = null;
        }
        size--;
        if (size == 1) {
            first = last;
        }
        return res;

    }

    /**
     * return an iterator over items in order from front to end.
     * 
     * @return instance of ListIteraor class
     */
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

}
