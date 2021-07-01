import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {

    private final Node dummy;
    private int dequeSize = 0;

    private class Node
    {
        Item item;
        Node next;
        Node prev;
    }

    // construct an empty deque
    public Deque()
    {
        dummy = new Node();
        dummy.next = dummy;
        dummy.prev = dummy;
        dequeSize = 0;
    }

    // is the deque empty?
    public boolean isEmpty()
    {
        return dequeSize == 0;
    }

    // return the numbers of items in the deque
    public int size()
    {
        return dequeSize;
    }

    // add the item to the front
    public void addFirst(Item item)
    {
        if (item == null)
        {
            throw new IllegalArgumentException(
                    "Error: Deque.addFirst(): Invalid argument: null");
        }
        Node newFront = new Node();
        newFront.item = item;
        newFront.next = dummy.next;
        newFront.prev = dummy;
        dummy.next.prev = newFront;
        dummy.next = newFront;
        ++dequeSize;
    }

    // add the item to the back
    public void addLast(Item item)
    {
        if (item == null)
        {
            throw new IllegalArgumentException(
                    "Error: Deque.addLast(): Invalid argument: null");
        }
        Node newBack = new Node();
        newBack.item = item;
        newBack.next = dummy;
        newBack.prev = dummy.prev;
        dummy.prev.next = newBack;
        dummy.prev = newBack;
        ++dequeSize;
    }

    // remove and return the item from the front
    public Item removeFirst()
    {
        if (isEmpty())
        {
            throw new java.util.NoSuchElementException(
                    "Error: Deque.removeFirst(): No such element");
        }
        Node oldFirst = dummy.next;
        dummy.next.next.prev = dummy;
        dummy.next = dummy.next.next;
        --dequeSize;
        return oldFirst.item;
    }

    // remove and return the item from the back
    public Item removeLast()
    {
        if (isEmpty())
        {
            throw new java.util.NoSuchElementException(
                    "Error: Deque.removeLast(): No such element");
        }
        Node oldLast = dummy.prev;
        dummy.prev.prev.next = dummy;
        dummy.prev = dummy.prev.prev;
        --dequeSize;
        return oldLast.item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator()
    {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item>
    {
        private Node current = dummy.next;

        public boolean hasNext()
        {
            return current != dummy;
        }

        public void remove()
        {
            throw new UnsupportedOperationException(
                    "Error: Deque.iterator.remove(): Unsupported operation");
        }

        public Item next()
        {
            if (current == dummy)
            {
                throw new java.util.NoSuchElementException(
                        "Error: Deque.iterator.next(): No such element");
            }
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    // unit testing
    public static void main(String[] args)
    {
        Deque<Integer> dq = new Deque<Integer>();
        System.out.println("Deque is empty?: " + dq.isEmpty());
        System.out.println("Deque size: " + dq.size());
        System.out.println("Adding 2 to the back of the deque");
        dq.addFirst(2);
        System.out.println("Deque is empty?: " + dq.isEmpty());
        System.out.println("Deque size: " + dq.size());
        System.out.println("Adding 1 to the front of the deque");
        dq.addFirst(1);
        System.out.println("Deque size: " + dq.size());
        System.out.println("Printing the deque using iterators");
        for (int i : dq)
            System.out.println(i);
        System.out.println("Removing the last item in the deque: "
                + dq.removeLast());
        System.out.println("Removing the first item in the deque: "
                + dq.removeFirst());
    }
}
