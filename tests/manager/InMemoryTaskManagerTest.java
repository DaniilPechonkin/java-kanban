package manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import tasks.Epic;
import tasks.Status;
import tasks.Subtask;
import tasks.Task;

import java.lang.reflect.Array;
import java.time.Duration;
import java.util.ArrayList;

class InMemoryTaskManagerTest {
    TaskManager taskManager = Manager.getDefaultTask();
    Task task1;
    Epic epic1;
    Subtask subtask1;
    Subtask subtask2;

    @BeforeEach
    public void beforeEach() {
        task1 = new tasks.Task("firstTask", "1");
        Task task2 = new tasks.Task("secondTask", "2");
        task1.setDuration(Duration.ofMinutes(1));
        task2.setStartTime(task1.getStartTime().plusMinutes(2));
        task2.setDuration(Duration.ofMinutes(2));
        taskManager.addTask(task1);
        taskManager.addTask(task2);

        epic1 = new tasks.Epic("firstEpic", "123");
        taskManager.addEpic(epic1);

        subtask1 = new tasks.Subtask("firstSubtask", "123",3);
        subtask2 = new tasks.Subtask("secondSubtask", "123",3);
        subtask1.setStartTime(subtask1.getStartTime().plusMinutes(5));
        subtask1.setDuration(Duration.ofMinutes(1));
        subtask2.setStartTime(subtask2.getStartTime().plusMinutes(10));
        subtask2.setDuration(Duration.ofMinutes(3));
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

    @Test
    public void canChangeStatus() {
        // Две со статусом NEW
        assertEquals(epic1.getStatus(), Status.NEW);

        //Две DONE и NEW
        subtask1.setStatus(Status.DONE);
        Subtask subtask3 = new Subtask("132", "213123", epic1.getId());
        subtask3.setStatus(Status.DONE);
        subtask3.setStartTime(subtask2.getStartTime().plusMinutes(50));
        subtask3.setDuration(Duration.ofMinutes(3));
        taskManager.addSubtask(subtask3);

        assertEquals(epic1.getStatus(), Status.IN_PROGRESS);

        //Две DONE
        subtask2.setStatus(Status.DONE);
        taskManager.removeSubtask(subtask3.getId());
        assertEquals(epic1.getStatus(), Status.DONE);

        //Три IN_PROGRESS
        subtask3.setStatus(Status.IN_PROGRESS);
        subtask1.setStatus(Status.IN_PROGRESS);
        subtask2.setStatus(Status.IN_PROGRESS);
        subtask2.setStartTime(subtask2.getStartTime().plusMinutes(100));
        subtask2.setDuration(Duration.ofMinutes(3));
        taskManager.addSubtask(subtask3);
        assertEquals(epic1.getStatus(), Status.IN_PROGRESS);
    }
}