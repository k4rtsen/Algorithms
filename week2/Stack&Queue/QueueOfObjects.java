public class QueueOfObjects<Item> {
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

    public void displayQueue() {
        if (first == null) {
            System.out.println("Queue is empty");
            return;
        }
        System.out.println("Display queue...");
        Node temp = first;
        int i = 0;
        while (temp != null) {
            System.out.println(++i + ".\t" + temp.item);
            temp = temp.next;
        }
    }

    public static void main(String[] args) {
        Integer n = 10;
        QueueOfObjects<Integer> queue = new QueueOfObjects<>();
        for (int i = 0; i < n; i++)
            queue.enqueue(i + 1);
        queue.displayQueue();

        for (int i = 0; i < n/2; i++)
            queue.dequeue();
        queue.displayQueue();
    }
}
