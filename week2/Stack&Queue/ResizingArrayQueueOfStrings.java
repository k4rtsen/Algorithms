public class ResizingArrayQueueOfStrings {
    private String[] q;
    private Integer head, tail;

    {
        head = 0;
        tail = 0;
    }

    public ResizingArrayQueueOfStrings() {
        q = new String[1];
    }

    public void enqueue(String item) {
        if (size() == q.length)
            resize(2 * q.length);
        q[tail] = item;
        tail++;
    }

    public String dequeue() {
        String result = q[head];
        q[head] = null;
        head++;
        if (size() > 0 && size() == q.length / 4)
            resize(q.length / 2);
        return result;
    }

    private void resize(int capacity) {
        String[] copy = new String[capacity];
        for (int i = 0; i < size(); i++)
            copy[i] = q[i + head];
        q = copy;
    }

    public boolean isEmpty() {
        return head == tail;
    }

    public int size() {
        return tail - head;
    }
}
