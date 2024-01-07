package manager;

public class Node {
    private int data;
    private Node next;

    public Node(int data) {
        this.data = data;
        this.next = null;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    public void removeNode(Node nodeToRemove) {
        Node current = this;
        Node previous = null;
        while (current != null) {
            if (current == nodeToRemove) {
                if (previous != null) {
                    previous.setNext(current.getNext());
                } else {
                    current = current.getNext();
                }
                return;
            }
            previous = current;
            current = current.getNext();
        }
    }
}

