package manager;

public class Manager {
    public HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

    public TaskManager getDefaultTask() {
        return new InMemoryTaskManager();
    }
}