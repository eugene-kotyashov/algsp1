import java.util.Iterator;

/**
 * @author Eugene Kotyashov
 *
 * @param <Item>
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
    private class Node {
        Item item;
        Node next;
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
    public final boolean isEmpty() {
        return (first == null) || (size == 0);
    }

    public final int size() // return the number of items on the queue
    {
        return size;
    }

    public final void enqueue(final Item item) // add the item
    {
        if (item == null) {
            throw new java.lang.NullPointerException();
        }
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.next = oldfirst;
        size++;
    }

    // delete and return a random item
    public final Item dequeue() {
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
        Node oldCurrent = current;
        current = current.next;
        if (rnd == 0) {
            first = current;
        }
        size--;
        return oldCurrent.item;
    }

    // return (but do not delete) a random item
    public final Item sample() {
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
    public final Iterator<Item> iterator() {
        return new RandomizedListIterator();
    }
}
