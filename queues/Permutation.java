import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

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
        RandomizedQueue<String> rq = new RandomizedQueue<String>();
        String s;
        int i = 0;
        while (!StdIn.isEmpty())
        {
            s = StdIn.readString();
            if (i < k)
            {
                rq.enqueue(s);
            }
            else if (!rq.isEmpty() && StdRandom.bernoulli(1.0 / (i + 1)))
            {
                rq.dequeue();
                rq.enqueue(s);
            }
            i++;
        }
        while (!rq.isEmpty())
        {
            s = rq.dequeue();
            System.out.println(s);
        }
    }
}
