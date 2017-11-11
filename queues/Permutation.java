public class Permutation {
    
    
    public static void main(String[] args) {
        if (args.length > 0)
        {
            int k = Integer.parseInt(args[0],10);
            RandomizedQueue<String> dq = new RandomizedQueue<String>();
            while (!StdIn.isEmpty())
            {
                dq.enqueue(StdIn.readString());
            }
            while (k-- > 0)
            {
                StdOut.println(dq.dequeue());
            }
        }
    }    
    
}
