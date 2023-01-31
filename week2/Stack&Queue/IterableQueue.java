import java.util.Iterator;

public class IterableQueue<Item> implements Iterable<Item> {
    private Node first, last;
    {
        first = null;
        last = null;
    }

    private class Node {
        Item item;
        Node next;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public void enqueue(Item item) {
        Node oldlast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        if (isEmpty())
            first = last;
        else
            oldlast.next = last;
    }

    public Item dequeue() {
        Item item = first.item;
        first = first.next;
        if (isEmpty())
            last = null;
        return item;
    }

    @Override
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
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    public static void main(String[] args) {
        int n = 10;
        IterableQueue<Integer> queue = new IterableQueue<>();
        for (int i = 0; i < n; i++)
            queue.enqueue(i);
        
        for (Integer val : queue) {
            System.out.println(val);
        }
        
    }
}