import java.util.Iterator;

public class Bag<Item> implements Iterable<Item> {
    private Node first;
    private Integer count;
    {
        first = null;
        count = 0;
    }

    private class Node {
        Item item;
        Node next;
    }

    public void add(Item item) {
        Node next = first;
        first = new Node();
        first.item = item;
        first.next = next;
        count++;
    }

    public int size() {
        return this.count;
    }

    @Override
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        Node currNode = first;

        @Override
        public boolean hasNext() {
            return currNode != null;
        }

        @Override
        public Item next() {
            Item item = currNode.item;
            currNode = currNode.next;
            return item;
        }
    }

    public static void main(String[] args) {
        final int n = 10;
        Bag<Integer> bag = new Bag<>();
        for (int i = 0; i < n; i++)
            bag.add(i);
        
        System.out.println("Size of bag: " + bag.size());
        for (int val : bag)
            System.out.print(val + "; ");
    }
}
