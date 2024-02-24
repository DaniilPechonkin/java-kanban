package manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.ArrayList;

class InMemoryTaskManagerTest {
    TaskManager taskManager = Manager.getDefaultTask();
    ArrayList<Task> tasks;
    ArrayList<Subtask> subtasks;
    ArrayList<Epic> epics;
    Task task1;
    Epic epic1;
    Subtask subtask1;

    @BeforeEach
    public void beforeEach() {
        tasks = new ArrayList<>();
        subtasks = new ArrayList<>();
        epics = new ArrayList<>();

        task1 = new tasks.Task("firstTask", "1");
        Task task2 = new tasks.Task("secondTask", "2");
        taskManager.addTask(task1);
        taskManager.addTask(task2);
        tasks.add(task1);
        tasks.add(task2);

        epic1 = new tasks.Epic("firstEpic", "123");
        taskManager.addEpic(epic1);
        epics.add(epic1);
        subtask1 = new tasks.Subtask("firstSubtask", "123",3);
        Subtask subtask2 = new tasks.Subtask("secondSubtask", "123",3);
        subtasks.add(subtask1);
        subtasks.add(subtask2);
        taskManager.addSubtask(subtask1);
        taskManager.addSubtask(subtask2);
    }

    @Test
    public void canGetAllTasks(){
        assertEquals(taskManager.getAllTasks(), tasks);
        assertEquals(taskManager.getAllEpics(), epics);
        assertEquals(taskManager.getAllSubtasks(), subtasks);
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
        tasks.remove(0);
        epics.remove(0);
        assertEquals(tasks, taskManager.getAllTasks());
        assertEquals(epics, taskManager.getAllEpics());
    }

    @Test
    public void canUpdateTask() {
        Task task3 = new Task("3", "3");
        taskManager.updateTask(task3);
        tasks.add(0, task3);
        assertEquals(tasks, taskManager.getAllTasks());
    }

}