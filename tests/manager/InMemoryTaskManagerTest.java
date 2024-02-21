package manager;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.ArrayList;

class InMemoryTaskManagerTest {
    TaskManager taskManager = Manager.getDefaultTask();

    @Test
    public void canGetAllTasks(){
        ArrayList<Task> tasks = new ArrayList<>();
        ArrayList<Subtask> subtasks = new ArrayList<>();
        ArrayList<Epic> epics = new ArrayList<>();

        Task task1 = new tasks.Task("firstTask", "1");
        Task task2 = new tasks.Task("secondTask", "2");
        taskManager.addTask(task1);
        taskManager.addTask(task2);
        tasks.add(task1);
        tasks.add(task2);

        Epic epic1 = new tasks.Epic("firstEpic", "123");
        taskManager.addEpic(epic1);
        epics.add(epic1);
        Subtask subtask1 = new tasks.Subtask("firstSubtask", "123",3);
        Subtask subtask2 = new tasks.Subtask("secondSubtask", "123",3);
        subtasks.add(subtask1);
        subtasks.add(subtask2);
        taskManager.addSubtask(subtask1);
        taskManager.addSubtask(subtask2);

        assertEquals(taskManager.getAllTasks(), tasks);
        assertEquals(taskManager.getAllEpics(), epics);
        assertEquals(taskManager.getAllSubtasks(), subtasks);
    }

}