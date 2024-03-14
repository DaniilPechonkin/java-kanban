package manager;

import tasks.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {
    private final Map<Integer, Node> nodes = new HashMap<>();
    private final List<Task> lastActivity = new LinkedList<>();

    @Override
    public List<Task> getHistory() {
        return new ArrayList<>(lastActivity);
    }

    @Override
    public void addInHistory(Task task) {
        if (task != null) {
            if (lastActivity.contains(task)) {
                lastActivity.remove(task.getId());
                lastActivity.add(task);
            }
            lastActivity.add(task);
        }
    }

    @Override
    public void remove(int id) {
        lastActivity.remove(id);
    }

    public void removeNode(Node node) {
        lastActivity.remove(node);
    }

    public void addLast(Task task) {
        nodes.put(task.getId(), new Node(task.getId()));
    }

    class DoubleLinkedList<T> {
        public Node<T> head;
        public Node<T> tail;
        public int size = 0;

        public void linkLast(T index) {
            Node node = new Node<>(index);
            if (tail == null) {
                head = node;
                tail = node;
            } else {
                tail.next = node;
                node.prev = tail;
                tail = node;
            }
        }

        public T getLast() {
            final Node<T> curTail = tail;
            return tail.index;
        }
    }
}

