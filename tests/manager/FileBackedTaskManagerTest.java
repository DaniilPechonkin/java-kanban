package manager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.*;
import manager.FileBackedTaskManager;
import java.io.IOException;
import java.util.ArrayList;
import java.time.Duration;

import static manager.FileBackedTaskManager.loadFromFile;
import static org.junit.jupiter.api.Assertions.*;

public class FileBackedTaskManagerTest {
    FileBackedTaskManager fileManager = new FileBackedTaskManager();
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
        task1 = new Task("firstTask", "1");
        task2 = new Task("secondTask", "2");
        task1.setDuration(Duration.ofMinutes(1));
        task2.setStartTime(task1.getStartTime().plusMinutes(2));
        task2.setDuration(Duration.ofMinutes(2));
        fileManager.addTask(task1);
        fileManager.addTask(task2);

        Epic epic1 = new Epic("firstEpic", "123");
        fileManager.addEpic(epic1);

        subtask1 = new Subtask("firstSubtask", "123",3);
        subtask2 = new Subtask("secondSubtask", "123",3);
        subtask1.setStartTime(subtask1.getStartTime().plusMinutes(5));
        subtask1.setDuration(Duration.ofMinutes(1));
        subtask2.setStartTime(subtask2.getStartTime().plusMinutes(10));
        subtask2.setDuration(Duration.ofMinutes(3));
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
            assertEquals(fileManager.getPrioritizedTasks(), newManager.getPrioritizedTasks());
        } catch (IOException exception){
            exception.printStackTrace();
        }
    }

    @Test
    public void testException() {
        Assertions.assertDoesNotThrow(() -> fileManager.addTask(task1));
    }
}
