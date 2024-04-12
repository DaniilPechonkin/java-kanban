package manager;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class FileBackedTaskManager extends InMemoryTaskManager {
    String FILENAME = "autosave.csv";

    void save(Task task, String filename) {
        try (FileWriter fileWriter = new FileWriter(filename)) {
                fileWriter.write(toString(task) + "\n");
        } catch (IOException e) {
            System.out.println("Произошла ошибка во время записи файла.");
        }
    }

    void loadFromFile(String filename) throws IOException {
        FileReader reader = new FileReader(filename);
        BufferedReader br = new BufferedReader(reader);
        while (br.ready()) {
            String str = br.readLine();
            String[] values = str.split(",");
            Task task = fromString(str);
            if (task.getClass().equals(Task.class)) {
                addTask(task);
            } else if (task.getClass().equals(Subtask.class)) {
                Subtask subtask = (Subtask) task;
                addSubtask(subtask);
            } else if (task.getClass().equals(Epic.class)) {
                Epic epic = (Epic) task;
                addEpic(epic);
            }
        }
        br.close();
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

    public static String toString(Task task) {
        if (task.getClass() == Subtask.class) {
            return task.getId() + "," + task.getClass() + ","+ task.getName() + "," + task.getStatus() + ","
                    + task.getDescription() + "," + ((Subtask) task).getEpicId() + "\n";
        } else {
            return task.getId() + "," + task.getClass() + ","+ task.getName() + "," + task.getStatus() + "," + task.getDescription() + "\n";
        }
    }

    public static Task fromString(String value) {
        String[] values = value.split(",");
        if (values[1].equals("SUBTASK")) {
            Subtask subtask = new Subtask(values[0], values[3], Integer.parseInt(values[5]));
            subtask.setName(values[2]);
            subtask.setDescription(values[4]);
            return subtask;
        } else if (values[1].equals("EPIC")){
            Epic epic = new Epic(values[0], values[3]);
            epic.setName(values[2]);
            epic.setDescription(values[4]);
            return epic;
        } else {
            Task task = new Task(values[0], values[3]);
            task.setName(values[2]);
            task.setDescription(values[4]);
            return task;
        }
    }

    static String historyToString(HistoryManager manager) {
        String history = "";
        for (Task task : manager.getHistory()) {
            history += toString(task);
        }
        return history;
    }

    static List<Task> historyFromString(String value) throws IOException{
        List<Task> history = new ArrayList<>();
        FileReader reader = new FileReader(value);
        BufferedReader br = new BufferedReader(reader);
        while (br.ready()) {
            String str = br.readLine();
            String[] values = str.split(",");
            Task task = fromString(str);
            history.add(task);
        }
        br.close();
        return history;
    }
}
