package manager;

import tasks.Task;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private final static int HISTORY_LENGTH = 10;
    private final List<Task> lastActivity = new LinkedList<>();

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
            lastActivity.add(task);
        }
    }
}