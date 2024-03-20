package manager;

import tasks.Task;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class InMemoryHistoryManager implements HistoryManager {
    public final DoubleLinkedList<Task> lastActivity = new DoubleLinkedList<>();

    @Override
    public List<Task> getHistory() {
        return lastActivity.getTasks();
    }

    @Override
    public void addInHistory(Task task) {
        if (task != null) {
            if (lastActivity.nodes.containsKey(task.getId())) {
                lastActivity.removeNode(task.getId());
            }
            lastActivity.linkLast(task);
        }
    }

    @Override
    public void remove(int id) {
        lastActivity.removeNode(id);
    }


    public static class Node<E> {
        public Task index;
        public Node<E> next;
        public Node<E> prev;

        public Node(Task index) {
            this.index = index;
        }
    }

    public class DoubleLinkedList<T> {
        public final Map<Integer, Node> nodes = new HashMap<>();
        public Node<T> head;
        public Node<T> tail;

        public void linkLast(Task task) {
            Node node = new Node<>(task);
            if (tail == null) {
                head = node;
            } else {
                tail.next = node;
                node.prev = tail;
            }
            tail = node;
        }

        public void addLast(Task task) {
            nodes.put(task.getId(), new Node(task));
        }

        public List<Task> getTasks() {
            List<Task> tasks = new ArrayList<>();
            Node curNode = head;
            while (curNode.next != null) {
                tasks.add(curNode.index);
                curNode = curNode.next;
            }
            return tasks;
        }

        public List<Node> getNodes() {
            List<Node> nodesList = new ArrayList<>();
            for (Node node : nodes.values()) {
                nodesList.add(node);
            }
            return nodesList;
        }

        public void removeNode(Integer id) {
            Node node = nodes.get(id);
            if (node.prev == null) {
                head = node.next;
                head.prev = null;
            } else if (node.next == null) {
                tail = node.prev;
                tail.next= null;
            } else if (node.prev == null && node.next == null) {
                head = null;
                tail = null;
            } else if (node.prev != null && node.next != null) {
                node.prev.next = node.next;
                node.next.prev = node.prev;
            }
            nodes.remove(id);
        }
    }
}

