package manager;

import tasks.Task;

import java.util.LinkedList;

public class InMemoryHistoryManager implements HistoryManager {
    private final static int HISTORY_LENGTH = 10;
    private final LinkedList<Task> lastActivity = new LinkedList<>();

    @Override
    public LinkedList<Task> getHistory() {
        return new LinkedList<>(lastActivity);
    }

    @Override
    public void addInHistory(Task task) {
        if (lastActivity.size() > HISTORY_LENGTH) {
            lastActivity.remove(0);
        }
        lastActivity.add(task);
    }
}
