public class Subset {
    
    
    public static void main(String[] args) {
        if (args.length > 0)
        {
            int k = new Integer(args[0]);
            Deque<String> dq = new Deque<String>();
            while (!StdIn.isEmpty())
            {
                dq.addFirst(StdIn.readString());
            }
            StdOut.println("size is "+ dq.size());
            for (String s : dq)
                StdOut.println(s);
        }
    }    
    
}
