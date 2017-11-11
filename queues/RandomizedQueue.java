/******************************************************************************
 *  Name:    Eugene Kotyashov
 *  NetID:   eugene
 *  Precept: P01
 *
 *  Partner Name:    N/A
 *  Partner NetID:   N/A
 *  Partner Precept: N/A
 * 
 *  Description:  Model a Random access queue data structure.
 ******************************************************************************/

import java.util.Iterator;

/**
 * @author Eugene Kotyashov
 * 
 * @param <Item>
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
    private class Node {
        private Item item;
        private Node next;
    }

    private int size = 0;
    private Node first = null;

    private class RandomizedListIterator implements Iterator<Item> {
        private int currentOrderId = 0;

        private Item[] order = (Item[]) (new Object[size]);

        public RandomizedListIterator() {
            Node current = first;
            for (int i = 0; i < order.length; i++) {
                order[i] = current.item;
                current = current.next;
            }
            StdRandom.shuffle(order);
        }

        public boolean hasNext() {
            return (currentOrderId < size);
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (order.length == 0) {
                throw new java.util.NoSuchElementException();
            }
            return order[currentOrderId++];
        }
    }

    // construct an empty randomized queue
    public RandomizedQueue() {

    }

    // is the queue empty?
    public boolean isEmpty() {
        return (first == null); //|| (size == 0);
    }

    public int size() // return the number of items on the queue
    {
        return size;
    }

    public void enqueue(Item item) // add the item
    {
        if (item == null) {
            throw new java.lang.IllegalArgumentException();
        }
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.next = oldfirst;
        size++;
    }

    // delete and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        int rnd = StdRandom.uniform(size);
        int i = 0;
        Node prev = null;
        Node cur = first;
        while (i < rnd) {
            prev = cur;
            cur = cur.next;
            i++;
        }
        Item it = cur.item;
        if (prev != null)
            prev.next = cur.next;
        else
            first = cur.next;
        size--;
        return it;
    }

    // return (but do not delete) a random item
    public Item sample() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        int rnd = StdRandom.uniform(size);
        int i = 0;
        Node current = first;
        while (i < rnd) {
            current = current.next;
            i++;
        }
        return current.item;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedListIterator();
    }
}
