package tasks;

import manager.HistoryManager;
import manager.Manager;
import manager.TaskManager;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AllTests {
    HistoryManager historyManager = Manager.getDefaultHistory();
    TaskManager taskManager = Manager.getDefaultTask();
    
    @Test
    public void isCopyOfTaskEqualsWithSameId() {
        Task task1 = new Task("first", "first");
        Task task2 = new Task("second", "second");
        Task task3 = task1;
        assertEquals(task1, task3);
        assertNotEquals(task1, task2);
    }

    @Test
    public void isCopyOfEpicEqualsWithSameId() {
        Epic epic1 = new Epic("first", "first");
        Epic epic2 = new Epic("second", "second");
        Task epic3 = epic1;
        assertEquals(epic1, epic3);
        assertNotEquals(epic1, epic2);
    }

    @Test
    public void canFindId() {
        Task task1 = new tasks.Task("firstTask", "1");
        Task task2 = new tasks.Task("secondTask", "2");
        Epic epic1 = new tasks.Epic("firstEpic", "123");
        taskManager.addEpic(epic1);
        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.findTask(1);
        taskManager.findTask(2);
        taskManager.findTask(3);
    }

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