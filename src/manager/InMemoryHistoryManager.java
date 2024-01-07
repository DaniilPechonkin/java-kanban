package manager;

//import org.w3c.dom.Node;
import tasks.Task;
import manager.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {
    private final static int HISTORY_LENGTH = 10;
    private final LinkedList<Task> lastActivity = new LinkedList() {
        public void linkLast(Task task) {
            lastActivity.add(task);
        }

        public ArrayList<Task> getTasks() {
            return new ArrayList<Task>(lastActivity);
        }
    };

    private final Map<Integer, Node> taskInputs = new HashMap<>();

    @Override
    public List<Task> getHistory() {
        return new ArrayList<>(lastActivity);
    }

    @Override
    public void addInHistory(Task task) {
        if (task != null) {
            if (lastActivity.size() > HISTORY_LENGTH) {
                lastActivity.remove(0);
            }
            if (lastActivity.contains(task)) {
                lastActivity.remove(task);
                taskInputs.put(task.getId(), new Node(lastActivity.size()));
            }
            lastActivity.addLast(task);
        }
    }

    @Override
    public void removeFromHistory(int id) {
        lastActivity.remove(id);
    }
}