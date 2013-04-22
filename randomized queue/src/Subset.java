public class Subset {

    public static void main(final String[] args) {
        if (args.length > 0) {
            int k = new Integer(args[0]);
            RandomizedQueue<String> dq = new RandomizedQueue<String>();
            while (!StdIn.isEmpty()) {
                dq.enqueue(StdIn.readString());
            }
            for (int i = 0; i < k; i++) {
                StdOut.println(dq.dequeue());
            }
        }
    }

}
