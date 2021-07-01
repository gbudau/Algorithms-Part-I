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
        int i = 0;
        int j;
        String s;
        while (!StdIn.isEmpty())
        {
            s = StdIn.readString();
            if (k == 0)
            {
                continue;
            }
            if (i < k)
            {
                rq.enqueue(s);
            }
            else
            {
                j = StdRandom.uniform(i + 1);
                if (j < k)
                {
                    rq.dequeue();
                    rq.enqueue(s);
                }
            }
            i++;
        }
        for (String str : rq)
        {
            System.out.println(str);
        }
    }
}
