public class StackOfObjects<Item> {
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

    public void displayStack() {
        if (first == null) {
            System.out.println("Stack is empty");
            return;
        }
        Node temp = first;
        int i = 0;
        while (temp != null) {
            System.out.println(++i + ".\t" + temp.item);
            temp = temp.next;
        }
    }

    public static void main(String[] args) {
        StackOfObjects<Integer> stack = new StackOfObjects<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        stack.displayStack();
        stack.pop();
        stack.pop();
        stack.displayStack();
        stack.pop();
        stack.pop();
        stack.displayStack();
    }
}