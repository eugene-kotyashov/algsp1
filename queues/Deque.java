import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    
    
    private class Node
    {
        Item item;
        Node next;     
    }
    
    private class ListIterator implements Iterator<Item>
    {
        private Node current = first;
        
        public boolean hasNext()
        {
            return current != null;
        }
        
        public void remove()
        {
            throw new UnsupportedOperationException();
        }
        
        public Item next()
        {
            if (current == null)
            {
                throw new java.util.NoSuchElementException();
            }
            Item res = current.item;
            current = current.next;
            return res;
        }
    }
    
    private Node first = null;
    private Node last = null;
    private int size = 0;
    
    public Deque()                     // construct an empty deque
    {       
    }
    public boolean isEmpty()           // is the deque empty?
    {
        return first == null;
    }
    public int size()                  // return the number of items on the deque
    {
        return size;
    }
    public void addFirst(Item item)    // insert the item at the front
    {
        if (item == null)
        {
            throw new java.lang.NullPointerException();
        }
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.next = oldfirst;
        size++;
    }
    public void addLast(Item item)     // insert the item at the end
    {
        if (item == null)
        {
            throw new java.lang.NullPointerException();
        }
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        oldLast.next = last;
    }
    public Item removeFirst()          // delete and return the item at the front
    {
        if (isEmpty())
        {
            throw new java.util.NoSuchElementException();
        }
        Item res = first.item;
        first = first.next;
        size--;
        return res;
    }
    public Item removeLast()           // delete and return the item at the end
    {
        if (isEmpty())
        {
            throw new java.util.NoSuchElementException();
        }
        Item res = last.item;
        last = null;
        return res;
        
    }
    
    public Iterator<Item> iterator()   // return an iterator over items in order from front to end
    {
        return new ListIterator();
    }
   
}