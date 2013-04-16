import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
   
   private class Node
   {
       Item item;
       Node next;
       Node prev;
   }
   
   private Node[] ids;
   private int size =0;   
   private Node first = null;
   
   private class RandomizedListIterator<Item> implements Iterator<Item>
   {       
       private Node current = first;
       
       private Node[] order;
       
       public RandomizedListIterator()
       {
           //StdStat
       }
       
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
   
   public RandomizedQueue()           // construct an empty randomized queue
   {
       
   }
   public boolean isEmpty()           // is the queue empty?
   {
       return size==0;
   }
   public int size()                  // return the number of items on the queue
   {
       return size;
   }
   public void enqueue(Item item)     // add the item
   {
       Item oldfirst = first;
       first = new Node();
       first.item = item;
       first.next = oldfirst;
       oldfirst.prev = first;
       size++;
   }
   public Item dequeue()              // delete and return a random item
   {
       
   }
   public Item sample()   // return (but do not delete) a random item
   {
   }
   public Iterator<Item> iterator()   // return an independent iterator over items in random order
   {
       return new RandomizedListIterator();
   }

    
}
