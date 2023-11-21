package manager;

import tasks.Task;

import java.util.ArrayList;

public class InMemoryHistoryManager implements HistoryManager{
    private static ArrayList<Task> lastActivity = new ArrayList<>();


    @Override
    public ArrayList<Task> getHistory() {
        return lastActivity;
    }

    @Override
    public void addInHistory(Task task) {
        if (lastActivity.size() < 10) {
            lastActivity.add(task);
        } else {
            lastActivity.remove(0);
            lastActivity.add(task);
        }
    }
}
