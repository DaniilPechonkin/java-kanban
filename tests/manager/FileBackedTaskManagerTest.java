package manager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static manager.FileBackedTaskManager.loadFromFile;
import static org.junit.jupiter.api.Assertions.*;

public class FileBackedTaskManagerTest {
    FileBackedTaskManager fileManager = new FileBackedTaskManager();
    InMemoryTaskManager taskManager = new InMemoryTaskManager();
    Task task1;
    Task task2;
    Subtask subtask1;
    Subtask subtask2;
    ArrayList<Task> tasks;
    ArrayList<Subtask> subtasks;
    ArrayList<Epic> epics;

    @BeforeEach
    public void beforeEach() {
        tasks = new ArrayList<>();
        subtasks = new ArrayList<>();
        epics = new ArrayList<>();
        task1 = new tasks.Task("firstTask", "1");
        task2 = new tasks.Task("secondTask", "2");
        fileManager.addTask(task1);
        fileManager.addTask(task2);

        Epic epic1 = new tasks.Epic("firstEpic", "123");
        fileManager.addEpic(epic1);

        subtask1 = new tasks.Subtask("firstSubtask", "123",3);
        subtask2 = new tasks.Subtask("secondSubtask", "123",3);
        fileManager.addSubtask(subtask1);
        fileManager.addSubtask(subtask2);

        tasks.add(task1);
        tasks.add(task2);
        subtasks.add(subtask1);
        subtasks.add(subtask2);
        epics.add(epic1);
    }

    @Test
    public void canLoadFromFile() {
        try {
            FileBackedTaskManager newManager = loadFromFile(fileManager.FILENAME);
            assertEquals(newManager.getAllTasks(), tasks);
            assertEquals(newManager.getAllEpics(), epics);
            assertEquals(newManager.getAllSubtasks(), subtasks);

            System.out.println(task1);
            List<Task> sortedTasks = new ArrayList<>();
            sortedTasks.add(task1);
            sortedTasks.add(task2);
            sortedTasks.add(subtask1);
            sortedTasks.add(subtask2);
            Set<Task> taskSet = taskManager.getPrioritizedTasks();
            List<Task> taskList = new ArrayList<>(taskSet);
            assertEquals(taskList,sortedTasks);
        } catch (IOException exception){
            exception.printStackTrace();
        }
    }

    @Test
    public void testException() {
        Assertions.assertDoesNotThrow(() -> fileManager.addTask(task1));
    }
}
