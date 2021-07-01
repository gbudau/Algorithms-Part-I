import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item>
{
    private Item[] data;
    private int size;

    // construct an empty randomized queue
    public RandomizedQueue()
    {
        data = null;
        size = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty()
    {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size()
    {
        return size;
    }


    // add the item
    public void enqueue(Item item)
    {
        if (item == null)
        {
            throw new IllegalArgumentException(
                    "Error: RandomizedQueue.enqueue(): illegal argument null");
        }
        if (isEmpty() || size == data.length)
        {
            resize(isEmpty() ? 1 : 2 * data.length);
        }
        if (isEmpty())
        {
            data[size++] = item;
        }
        else
        {
            final int randomIndex = StdRandom.uniform(size);
            Item temp = data[randomIndex];
            data[randomIndex] = item;
            data[size++] = temp;
        }
    }

    // resize the randomized queue
    private void resize(int capacity)
    {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < size; i++)
        {
            copy[i] = data[i];
        }
        data = copy;
    }

    // remove  and return a random item
    public Item dequeue()
    {
        if (isEmpty())
        {
            throw new java.util.NoSuchElementException(
                    "Error: RandomizedQueue.dequeue(): no such element");
        }
        final int randomIndex = StdRandom.uniform(size);
        Item item = data[randomIndex];
        data[randomIndex] = data[--size];
        data[size] = null;
        if (!isEmpty() && size == data.length / 4)
        {
            resize(data.length / 2);
        }
        return item;
    }
    
    // return a random item (but do not remove it)
    public Item sample()
    {
        if (isEmpty())
        {
            throw new java.util.NoSuchElementException(
                    "Error: RandomizedQueue.sample(): no such element");
        }
        final int index = StdRandom.uniform(size - 1);
        return data[index];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator()
    {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item>
    {
        final private int start;
        private int index;

        RandomizedQueueIterator()
        {
            start = StdRandom.uniform(size);
            index = start;
        }

        public boolean hasNext()
        {
            if (isEmpty())
            {
                return false;
            }
            else
            {
                return (index + 1) % size != start;
            }
        }

        public void remove()
        {
            throw new UnsupportedOperationException(
                    "Error: RandomizedQueue.iterator.remove()");
        }

        public Item next()
        {
            if (!hasNext())
            {
                throw new java.util.NoSuchElementException(
                        "Error: RandomizedQueue.iterator.next()");
            }
            index = (index + 1) % size;
            return data[index];
        }
    }

    // unit testing
    public static void main(String[] args)
    {
        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
        System.out.println("RandomizedQueue is empty?: " + rq.isEmpty());
        System.out.println("RandomizedQueue size: " + rq.size());
        System.out.println("Adding 2 to the RandomizedQueue");
        rq.enqueue(2);
        System.out.println("RandomizedQueue is empty?: " + rq.isEmpty());
        System.out.println("RandomizedQueue size: " + rq.size());
        System.out.println("Adding 1 to the RandomizedQueue");
        rq.enqueue(1);
        System.out.println("RandomizedQueue size: " + rq.size());
        System.out.println("Adding 10 random integers to the RandomizedQueue");
        for (int i = 0; i < 10; ++i)
        {
            rq.enqueue(StdRandom.uniform(Integer.MAX_VALUE));
        }
        System.out.println("RandomizedQueue size: " + rq.size());
        System.out.println();
        System.out.println("Printing the RandomizedQueue using iterators");
        for (int i : rq)
        {
            System.out.println(i);
        }
        System.out.println();
        System.out.println("Printing the RandomizedQueue using iterators");
        for (int i : rq)
        {
            System.out.println(i);
        }
        System.out.println();
        System.out.println("Removing all the items from the RandomizedQueue");
        while (!rq.isEmpty())
        {
            System.out.println("Removing one item from the RandomizedQueue: "
                    + rq.dequeue());
        }

    }
}
