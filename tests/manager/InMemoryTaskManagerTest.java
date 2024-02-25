package manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import tasks.Epic;
import tasks.Status;
import tasks.Subtask;
import tasks.Task;

import java.util.ArrayList;

class InMemoryTaskManagerTest {
    TaskManager taskManager = Manager.getDefaultTask();
    Task task1;
    Epic epic1;
    Subtask subtask1;

    @BeforeEach
    public void beforeEach() {
        task1 = new tasks.Task("firstTask", "1");
        Task task2 = new tasks.Task("secondTask", "2");
        taskManager.addTask(task1);
        taskManager.addTask(task2);

        epic1 = new tasks.Epic("firstEpic", "123");
        taskManager.addEpic(epic1);

        subtask1 = new tasks.Subtask("firstSubtask", "123",3);
        Subtask subtask2 = new tasks.Subtask("secondSubtask", "123",3);
        taskManager.addSubtask(subtask1);
        taskManager.addSubtask(subtask2);
    }

    @Test
    public void canGetAllTasks(){
        assertEquals(taskManager.getAllTasks().size(), 2);
        assertEquals(taskManager.getAllEpics().size(), 1);
        assertEquals(taskManager.getAllSubtasks().size(), 2);
    }

    @Test
    public void canGetId() {
        assertEquals(taskManager.findTask(1), task1);
        assertEquals(taskManager.findEpic(3), epic1);
        assertEquals(taskManager.findSubtask(4), subtask1);
    }

    @Test
    public void canRemoveTask() {
        taskManager.removeTask(1);
        taskManager.removeEpic(3);
        assertNull(taskManager.findTask(1));
        assertNull(taskManager.findEpic(3));
    }

    @Test
    public void canUpdateTask() {
        task1.setName("new name");
        taskManager.updateTask(task1);
        assertEquals(taskManager.findTask(task1.getId()), task1);

        assertEquals(taskManager.findEpic(epic1.getId()).getStatus(), Status.NEW);
        subtask1.setStatus(Status.DONE);
        taskManager.updateSubtask(subtask1);
        assertEquals(taskManager.findEpic(epic1.getId()).getStatus(), Status.IN_PROGRESS);
    }

}