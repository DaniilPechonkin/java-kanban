package manager;

import tasks.Task;

import java.util.List;

public interface HistoryManager {
    List<Task> getHistory();

    void addInHistory(Task task);
    void removeFromHistory(int id);
}
