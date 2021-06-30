public class RandomizedQueue<Item> implements Iterable<Item>
{
	private Item[] rq;
	private int size;

	// construct an empty randomized queue
	public RandomizedQueue()
	{
		size = 0;
	}

	// is the randomized queue empty?
	public boolean isEmpty()
	{
		return size == 0;
	}

	// return the number of items on the randomized queue
	public void enqueue(Item item)
	{
		if (item == null)
		{
			throw new IllegalArgumentException("Error: RandomizedQueue.enqueue(): illegal argument null);
		}
	}

	// remove  and return a random item
	public item dequeue()
	{
		if (isEmpty())
		{
			throw new java.util.NoSuchElementException("Error: RandomizedQueue.dequeue(): no such element);
		}
	}
	
	// return a random item (but do not remove it)
	public Item sample()
	{
		if (isEmpty())
		{
			throw new java.util.NoSuchElementException("Error: RandomizedQueue.sample(): no such element);
		}
	}

	// return an independent iterator over items in random order
	public Iterator<Item> iterator()
	{
		return new RandomizedQueueIterator();
	}

	// unit testing
	public static void main(String[] args)
	{
	}
}
