package manager;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.io.*;

public class FileBackedTaskManager extends InMemoryTaskManager {
    String FILENAME = "autosave.csv";

    private void save() {
        try (FileWriter fileWriter = new FileWriter(FILENAME)) {
            fileWriter.write("id,type,name,status,description,startTime,duration,epic" + "\n");
            for (Task task : getAllTasks()) {
                fileWriter.write(ToFromString.toString(task));
            }
            for (Epic epic : getAllEpics()) {
                fileWriter.write(ToFromString.toString(epic));
            }
            for (Subtask subtask :getAllSubtasks()) {
                fileWriter.write(ToFromString.toString(subtask));
            }
        } catch (IOException e) {
            System.out.println("Произошла ошибка во время записи файла.");
        }
    }

    public static FileBackedTaskManager loadFromFile(String path) throws IOException {
        FileBackedTaskManager manager = new FileBackedTaskManager();
        FileReader reader = new FileReader(path);
        BufferedReader br = new BufferedReader(reader);
        while (br.ready()) {
            String str = br.readLine();
            if (!str.equals("id,type,name,status,description,startTime,duration,epic")) {
                Task task = ToFromString.fromString(str);
                if (task.getClass().equals(Epic.class)) {
                    Epic epic = (Epic) task;
                    manager.epics.put(epic.getId(), epic);
                } else if (task.getClass().equals(Subtask.class)) {
                    Subtask subtask = (Subtask) task;
                    manager.subtasks.put(subtask.getId(), subtask);
                    Epic epic = manager.epics.get(subtask.getEpicId());
                    epic.getSubtasksId().add(subtask.getId());
                    manager.epics.put(epic.getId(), epic);
                    manager.sortedByTimeTasks.add(subtask);
                } else if (task.getClass().equals(Task.class)) {
                    manager.tasks.put(task.getId(), task);
                    manager.sortedByTimeTasks.add(task);
                }
            }
        }
        br.close();
        return manager;
    }

    @Override
    public void addSubtask(Subtask subtask) {
        super.addSubtask(subtask);
        save();
    }

    @Override
    public void addTask(Task task) {
        super.addTask(task);
        save();
    }

    @Override
    public void addEpic(Epic epic) {
        super.addEpic(epic);
        save();
    }
    @Override
    public void removeTask(Integer id)  {
        super.removeTask(id);
        save();
    }

    @Override
    public void removeEpic(Integer id) {
        super.removeEpic(id);
        save();
    }

    @Override
    public void removeSubtask(Integer id) {
        super.removeSubtask(id);
        save();
    }

    @Override
    public void removeAllTasks() {
        super.removeAllTasks();
        save();
    }

    @Override
    public void removeAllEpics() {
        super.removeAllEpics();
        save();
    }

    @Override
    public void removeAllSubtasks() {
        super.removeAllSubtasks();
        save();
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        super.updateSubtask(subtask);
        save();
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
    }
}
