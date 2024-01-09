package manager;

import tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {
    class Node<E> {
        public E data;
        public Node<E> next;
        public Node<E> prev;

        public Node(Node<E> prev, E data, Node<E> next) {
            this.data = data;
            this.next = next;
            this.prev = prev;
        }
    }

    public class LastActivity<T> {
        private ArrayList list;
        private Node<T> head;
        private Node<T> tail;
        private int size = 0;

        public void addFirst(T element) {
            final Node<T> oldHead = head;
            final Node<T> newNode = new Node<>(null, element, oldHead);
            head = newNode;
            if (oldHead == null)
                tail = newNode;
            else
                oldHead.prev = newNode;
            size++;
        }

        public T getFirst() {
            return head.data;
        }

        public void addLast(T element) {
            final Node<T> oldTail = tail;
            final Node<T> newNode = new Node<>(null, element, oldTail);
            tail = newNode;
            if (oldTail == null) {
                head = newNode;
            } else {
                oldTail.prev = newNode;
            }
            size++;
        }

        public T getLast() {
            return tail.data;
        }

        public int size() {
            return this.size;
        }

        public void remove(T value) {
            list.remove(value);
        }

        public boolean contains(T value) {
            return list.contains(value);
        }
    }

    LastActivity lastActivity = new LastActivity();
    private final Map<Integer, Node> taskInputs = new HashMap<>();

    @Override
    public void addInHistory(Task task) {
        if (task != null) {
            if (lastActivity.contains(task)) {
                lastActivity.remove(task);
            }
            lastActivity.addLast(task);
        }
    }

    @Override
    public void removeFromHistory(int id) {
        lastActivity.remove(id);
    }
}