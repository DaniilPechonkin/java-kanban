package manager;

import tasks.Epic;
import tasks.Status;
import tasks.Subtask;
import tasks.Task;

import java.io.FileWriter;
import java.io.IOException;


public class FileBackedTaskManager extends InMemoryTaskManager {
    String FILENAME = "autosave";
    void save(Task task, String filename) {
        String toSave = toString(task);
        try (FileWriter fileWriter = new FileWriter(filename)) {
                fileWriter.write(toSave + "\n");

        } catch (IOException e) {
            System.out.println("Произошла ошибка во время записи файла.");
        }
    }

    @Override
    public void addSubtask(Subtask subtask) {
        super.addSubtask(subtask);
        save(subtask, FILENAME);
    }

    @Override
    public void addTask(Task task) {
        super.addTask(task);
        save(task, FILENAME);
    }

    @Override
    public void addEpic(Epic epic) {
        super.addEpic(epic);
        save(epic, FILENAME);
    }

    public String toString(Task task) {
        return task.getId() + "," + task.getClass() + ","+ task.getName() + "," + task.getStatus() + "," + task.getDescription();
    }

    public Task fromString(String value) {
        String[] values = value.split(",");
        Task task = new Task(values[0], values[3]);
        task.setName(values[2]);
        task.setDescription(values[4]);
        return task;
    }
}
