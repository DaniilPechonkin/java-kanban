package manager;

import tasks.Task;

import java.util.LinkedList;

public interface HistoryManager {
    LinkedList<Task> getHistory();

    public void addInHistory(Task task);
}
