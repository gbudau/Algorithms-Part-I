import edu.princeton.cs.algs4.StdIn;

public class Permutation
{
    public static void main(String[] args)
    {
        if (args.length != 1)
        {
            System.out.println("Usage: java Permutation k");
            return;
        }
        int k = Integer.parseInt(args[0]);
        if (k <= 0)
        {
            System.out.println("Error: k must be a positive number");
            return;
        }
        RandomizedQueue<String> rq = new RandomizedQueue<String>();
        String s;
        while (!StdIn.isEmpty())
        {
            s = StdIn.readString();
            rq.enqueue(s);
        }
        while (!rq.isEmpty() && k != 0)
        {
            s = rq.dequeue();
            System.out.println(s);
            k--;
        }
    }
}
