package tasks;

import manager.InMemoryTaskManager;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {
    InMemoryTaskManager taskManager = new InMemoryTaskManager();

    @Test
    void addNewTask() {
        Task task = new Task("epic", "epic");
        task.setStartTime(task.getStartTime().plusMinutes(50));
        task.setDuration(Duration.ofMinutes(3));
        taskManager.addTask(task);
        Task savedTask= taskManager.findTask(task.getId());

        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(task, savedTask, "Задачи не совпадают.");

        final ArrayList<Task> tasks = taskManager.getAllTasks();

        assertNotNull(tasks, "Задачи не возвращаются.");
        assertEquals(1, tasks.size(), "Неверное количество задач.");
        assertEquals(task, tasks.get(0), "Задачи не совпадают.");
    }

    @Test
    public void canFindId() {
        Task task1 = new tasks.Task("firstTask", "1");
        Task task2 = new tasks.Task("secondTask", "2");
        Epic epic1 = new tasks.Epic("firstEpic", "123");
        assertEquals(taskManager.findTask(2).getId(), task2.getId());
    }

    @Test
    public void isCopyOfTaskEqualsWithSameId() {
        Task task1 = new Task("first", "first");
        Task task2 = new Task("second", "second");
        Task task3 = task1;
        assertEquals(task1, task3);
        assertNotEquals(task1, task2);
    }
}