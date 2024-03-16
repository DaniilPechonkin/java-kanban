package manager;

import tasks.Task;

import java.util.List;

public interface HistoryManager {
    InMemoryHistoryManager.DoubleLinkedList<Task> getHistory();

    void addInHistory(Task task);

    void remove(int id);
}