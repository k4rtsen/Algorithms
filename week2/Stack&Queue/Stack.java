public class Stack<Item> {
    private Node first = null;

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
        Node temp = first;
        int i = 0;
        while (temp != null) {
            System.out.println(++i + ".\t" + temp.item);
            temp = temp.next;
        }
    }

    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<>();
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