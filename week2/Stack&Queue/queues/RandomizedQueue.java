import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Node head, tail; // ref to first and tail items
    private int size; // count of elements

    private class Node {
        Item item;
        Node next;
        {
            item = null;
            next = null;
        }
    }

    {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    // создаем пустую рандомизированную очередь
    public RandomizedQueue() {
    }

    // пуста ли рандомизированная очередь?
    public boolean isEmpty() {
        return this.head == null;
    }

    // вернуть количество элементов в рандомизированной очереди
    public int size() {
        return this.size;
    }

    // добавить элемент
    public void enqueue(Item item) {
        if (item == null)
            throw new IllegalArgumentException();
        Node oldtail = this.tail;
        this.tail = new Node();
        this.tail.item = item;
        this.tail.next = null;
        if (isEmpty())
            this.head = this.tail;
        else
            oldtail.next = this.tail;
        this.size++;
    }

    private Node getRandomNode(final int randNum) {
        Node result = null;
        int iter = 0;
        while (result == null)
            for (Node node = this.head; node != null; node = node.next) {
                if (iter == randNum) {
                    result = node;
                    break;
                }
                iter++;
            }
        return result;
    }

    // удалить и вернуть случайный элемент
    public Item dequeue() {
        if (isEmpty())
            throw new NoSuchElementException();
        Node result = getRandomNode(StdRandom.uniformInt(this.size));
        if (result != head) {
            for (Node node = head; node != null; node = node.next) {
                if (node.next == result) {
                    node.next = result.next;
                    break;
                }
            }
        } else {
            head = head.next;
        }
        this.size--;
        return result.item;
    }

    // вернуть случайный элемент (но не удалять его)
    public Item sample() {
        if (isEmpty())
            throw new NoSuchElementException();
        return getRandomNode(StdRandom.uniformInt(this.size)).item;
    }

    // вернуть независимый итератор для элементов в случайном порядке
    public Iterator<Item> iterator() {
        ListIterator li = new ListIterator();
        return li;
    }

    private class ListIterator implements Iterator<Item> {
        private Node first;
        private int count;
        {
            first = null;
            count = 0;
        }

        ListIterator() {
            copyQueue();
        }

        private void copyQueue() {
            for (Node node = head; node != null; node = node.next) {
                if (first == null) {
                    first = new Node();
                    first.item = node.item;
                } else {
                    Node temp = new Node();
                    temp = first;
                    first = new Node();
                    first.item = node.item;
                    first.next = temp;
                }
                count++;
            }
        }

        @Override
        public boolean hasNext() {
            return first != null;
        }

        @Override
        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException();
            Item result = null;
            if (count == 1) {
                result = first.item;
                first = null;
                --count;
                return result;
            }
            int countOfIter = StdRandom.uniformInt(count - 1);
            int iter = 0;
            for (Node node = first; node != null; node = node.next) {
                if (countOfIter == 0) {
                    result = first.item;
                    first = first.next;
                    --count;
                    return result;
                }
                if (countOfIter == iter) {
                    result = node.next.item;
                    node.next = node.next.next;
                    --count;
                    return result;
                }
                iter++;
            }
            return result;
        }
    }

    // модульное тестирование (обязательно)
    public static void main(String[] args) {
        RandomizedQueue<Integer> queue = new RandomizedQueue<>();
         queue.enqueue(806);
         queue.enqueue(207);
         queue.enqueue(876);
         StdOut.println(queue.dequeue());
         queue.enqueue(143);
         queue.enqueue(251);
         queue.enqueue(49);
         queue.enqueue(803);
         queue.enqueue(345);
         StdOut.println(queue.dequeue());
    }
}