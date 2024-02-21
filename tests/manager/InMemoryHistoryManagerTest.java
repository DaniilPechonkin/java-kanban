package manager;

import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.Task;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {
    TaskManager taskManager = Manager.getDefaultTask();
    HistoryManager historyManager = Manager.getDefaultHistory();

    @Test
    public void canGetHistory() {
        Task task1 = new tasks.Task("firstTask", "1");
        Task task2 = new tasks.Task("secondTask", "2");
        Epic epic1 = new tasks.Epic("firstEpic", "123");
        taskManager.addEpic(epic1);
        taskManager.addTask(task1);
        taskManager.addTask(task2);
        System.out.println(historyManager.getHistory());
    }
}