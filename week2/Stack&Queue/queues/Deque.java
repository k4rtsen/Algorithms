import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {
    private Node first, last; // ref to first and last items
    private int count; // count of elements

    private class Node {
        Item item;
        Node next;
    }

    {
        this.first = null;
        this.last = null;
        this.count = 0;
    }

    // construct an empty deque
    public Deque() {

    }

    // is the deque empty?
    public boolean isEmpty() {
        return this.first == null;
    }

    // return the number of items on the deque
    public int size() {
        return this.count;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null)
            throw new IllegalArgumentException();
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.next = oldfirst;
        if (last == null)
            last = first;
        count++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null)
            throw new IllegalArgumentException();
        Node oldlast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        if (isEmpty())
            first = last;
        else
            oldlast.next = last;
        count++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty())
            throw new NoSuchElementException();
        if (last == first)
            last = first.next;
        Item item = first.item;
        first = first.next;
        --count;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty())
            throw new NoSuchElementException();

        // if one element in collection
        if (first == last) {
            Item item = last.item;
            first = last.next;
            last = last.next;
            --count;
            return item;
        }

        // otherwise
        Node prelast = first;
        while (prelast.next != last) {
            // find prelast element
            prelast = prelast.next;
        }
        last = prelast;
        prelast = last.next; // save last for return
        last.next = null; // delete last from collection
        --count;
        return prelast.item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        final int N = 10;
        Deque<Integer> deque = new Deque<>();
        StdOut.println("Is deque is empty?: " + deque.isEmpty());
        for (int i = 0; i < N; i++) {
            deque.addFirst(i);
            deque.addLast(i);
        }
        StdOut.println("Size of deque: " + deque.size());
        for (Integer val : deque) {
            StdOut.print(val + "; ");
        }
        for (int i = 0; i < N / 2; i++) {
            deque.removeFirst();
            deque.removeLast();
        }
        StdOut.println("\nSize of deque: " + deque.size());
        for (Integer val : deque) {
            StdOut.print(val + "; ");
        }
    }
}
