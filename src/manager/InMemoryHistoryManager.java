package manager;

import tasks.Task;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class InMemoryHistoryManager implements HistoryManager {
    public final DoubleLinkedList<Task> lastActivity = new DoubleLinkedList<>();

    @Override
    public DoubleLinkedList<Task> getHistory() {
        return lastActivity;
    }

    @Override
    public void addInHistory(Task task) {
        if (task != null) {
            if (lastActivity.nodes.containsKey(task.getId())) {
                lastActivity.removeNode(task.getId());
            }
            lastActivity.addLast(task);
        }
    }

    @Override
    public void remove(int id) {
        lastActivity.removeNode(id);
    }


    public static class Node<E> {
        public E index;
        public Node<E> next;
        public Node<E> prev;

        public Node(E index) {
            this.index = index;
        }
    }

    public class DoubleLinkedList<T> {
        private final Map<Integer, Node> nodes = new HashMap<>();
        public Node<T> head;
        public Node<T> tail;

        public void linkLast(T index) {
            Node node = new Node<>(index);
            if (tail == null) {
                head = node;
            } else {
                tail.next = node;
                node.prev = tail;
            }
            tail = node;
        }

        public T getLast() {
            final Node<T> curTail = tail;
            return tail.index;
        }

        public void addLast(Task task) {
            nodes.put(task.getId(), new Node(task.getId()));
        }

        public List<Node> getNodes() {
            List<Node> nodesList = new ArrayList<>();
            for (Node node : nodes.values()) {
                nodesList.add(node);
            }
            return nodesList;
        }

        public void removeLastNode() {
            for (Node node : nodes.values()) {
                if (tail == null) {
                    nodes.remove(node);
                }
            }
        }

        public void removeFirstNode() {
            for (Node node : nodes.values()) {
                if (head == null) {
                    nodes.remove(node);
                }
            }
        }

        public void removeNode(Integer id) {
            for (Integer i : nodes.keySet()) {
                if (i == id) {
                    nodes.remove(id);
                }
            }
        }
    }
}

