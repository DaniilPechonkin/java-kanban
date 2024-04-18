package manager;

import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;


import java.io.IOException;
import java.util.ArrayList;

import static manager.FileBackedTaskManager.loadFromFile;
import static org.junit.jupiter.api.Assertions.*;

public class FileBackedTaskManagerTest {
    FileBackedTaskManager fileManager = new FileBackedTaskManager();

    @Test
    public void canLoadFromFile() {
        Task task1 = new tasks.Task("firstTask", "1");
        Task task2 = new tasks.Task("secondTask", "2");
        fileManager.addTask(task1);
        fileManager.addTask(task2);

        Epic epic1 = new tasks.Epic("firstEpic", "123");
        fileManager.addEpic(epic1);

        Subtask subtask1 = new tasks.Subtask("firstSubtask", "123",3);
        Subtask subtask2 = new tasks.Subtask("secondSubtask", "123",3);
        fileManager.addSubtask(subtask1);
        fileManager.addSubtask(subtask2);

        ArrayList<Task> tasks = new ArrayList<>();
        ArrayList<Subtask> subtasks = new ArrayList<>();
        ArrayList<Epic> epics = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        subtasks.add(subtask1);
        subtasks.add(subtask2);
        epics.add(epic1);

        try {
            FileBackedTaskManager newManager = loadFromFile(fileManager.FILENAME);
            assertEquals(newManager.getAllTasks(), tasks);
            assertEquals(newManager.getAllEpics(), epics);
            assertEquals(newManager.getAllSubtasks(), subtasks);
        } catch (IOException exception){
            exception.printStackTrace();
        }

    }
}
