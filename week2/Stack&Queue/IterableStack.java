import java.util.Iterator;

public class IterableStack<Item> implements Iterable<Item> {
    private Node first;
    {
        first = null;
    }

    private class Node {
        Item item;
        Node next;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public void push(Item item) {
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.next = oldfirst;
    }

    public Item pop() {
        Item item = first.item;
        first = first.next;
        return item;
    }

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
        IterableStack<Integer> stack = new IterableStack<>();
        for (int i = 0; i < n; i++) {
            stack.push(i);
        }
        for (Integer val : stack) {
            System.out.println(val);
        }
    }
}
