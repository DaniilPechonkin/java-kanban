package manager;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.io.*;


public class FileBackedTaskManager extends InMemoryTaskManager {
    String FILENAME = "autosave.csv";
    String HISTORY_FILE = "history.csv";

    void save(Task task) {
        try (FileWriter fileWriter = new FileWriter(FILENAME)) {
                fileWriter.write(ToFromString.toString(task) + "\n");
        } catch (IOException e) {
            System.out.println("Произошла ошибка во время записи файла.");
        }
    }

    Task saveInHistory(Task task) {
        try (FileWriter fileWriter = new FileWriter(HISTORY_FILE)) {
            fileWriter.write(ToFromString.toString(task) + "\n");
        } catch (IOException e) {
            System.out.println("Произошла ошибка во время записи файла.");
        }
        return task;
    }

     FileBackedTaskManager loadFromFile() throws IOException {
        FileBackedTaskManager manager = new FileBackedTaskManager();
        FileReader reader = new FileReader(FILENAME);
        BufferedReader br = new BufferedReader(reader);
        while (br.ready()) {
            String str = br.readLine();
            Task task = ToFromString.fromString(str);
            if (task.getClass().equals(Task.class)) {
                manager.addTask(task);
            } else if (task.getClass().equals(Subtask.class)) {
                Subtask subtask = (Subtask) task;
                manager.addSubtask(subtask);
            } else if (task.getClass().equals(Epic.class)) {
                Epic epic = (Epic) task;
                manager.addEpic(epic);
            }
        }
        br.close();

        FileReader historyReader = new FileReader(HISTORY_FILE);
        BufferedReader hbr = new BufferedReader(historyReader);
        while (hbr.ready()) {
            String str = hbr.readLine();
            String[] history = str.split(",");
            for (String i : history) {
                Integer id = Integer.parseInt(i);
                if (manager.getAllTasks().contains(id)) {
                    Task task = manager.findTask(id);
                    manager.getHistoryManager().addInHistory(task);
                } else if (manager.getAllSubtasks().contains(id)) {
                    Subtask subtask = manager.findSubtask(id);
                    manager.getHistoryManager().addInHistory(subtask);
                } else if (manager.getAllEpics().contains(id)) {
                    Epic epic = manager.findEpic(id);
                    manager.getHistoryManager().addInHistory(epic);
                }
            }
        }
        hbr.close();

        return manager;
    }

    @Override
    public void addSubtask(Subtask subtask) {
        super.addSubtask(subtask);
        save(subtask);
    }

    @Override
    public void addTask(Task task) {
        super.addTask(task);
        save(task);
    }

    @Override
    public void addEpic(Epic epic) {
        super.addEpic(epic);
        save(epic);
    }
    @Override
    public void removeTask(Integer id)  {
        super.removeTask(id);
        for (Task task : getAllTasks()) {
            save(task);
        }
        for (Subtask subtask : getAllSubtasks()) {
            save(subtask);
        }
        for(Epic epic :getAllEpics()) {
            save(epic);
        }
    }

    @Override
    public void removeEpic(Integer id) {
        super.removeEpic(id);
        for (Task task : getAllTasks()) {
            save(task);
        }
        for (Subtask subtask : getAllSubtasks()) {
            save(subtask);
        }
        for(Epic epic :getAllEpics()) {
            save(epic);
        }
    }

    @Override
    public void removeSubtask(Integer id) {
        super.removeSubtask(id);
        for (Task task : getAllTasks()) {
            save(task);
        }
        for (Subtask subtask : getAllSubtasks()) {
            save(subtask);
        }
        for(Epic epic :getAllEpics()) {
            save(epic);
        }
    }

    @Override
    public void removeAllTasks() {
        super.removeAllTasks();
        for (Task task : getAllTasks()) {
            save(task);
        }
        for (Subtask subtask : getAllSubtasks()) {
            save(subtask);
        }
        for(Epic epic :getAllEpics()) {
            save(epic);
        }
    }

    @Override
    public void removeAllEpics() {
        super.removeAllEpics();
        for (Task task : getAllTasks()) {
            save(task);
        }
        for (Subtask subtask : getAllSubtasks()) {
            save(subtask);
        }
        for(Epic epic :getAllEpics()) {
            save(epic);
        }
    }

    @Override
    public void removeAllSubtasks() {
        super.removeAllSubtasks();
        for (Task task : getAllTasks()) {
            save(task);
        }
        for (Subtask subtask : getAllSubtasks()) {
            save(subtask);
        }
        for(Epic epic :getAllEpics()) {
            save(epic);
        }
    }

    @Override
    public Epic findEpic(int id) {
        Epic epic;
        return epic = (Epic) saveInHistory(super.findEpic(id));
    }

    @Override
    public Subtask findSubtask(int id) {
        Subtask subtask;
        return subtask = (Subtask) saveInHistory(super.findSubtask(id));
    }

    @Override
    public Task findTask(int id) {
        return saveInHistory(super.findTask(id));
    }
}
