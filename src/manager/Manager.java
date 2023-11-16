package manager;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;

public class Manager {
    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();
    private final HashMap<Integer, Subtask> subtasks = new HashMap<>();
    private int count = 0;

    private int addId() {
        return ++count;
    }

    //методы создания
    public void addTask(Task newTask) {
        int id = addId();
        newTask.setId(id);
        tasks.put(id, newTask);
    }

    public void addSubtask(Subtask newSubtask) {
        for (Integer epicId : epics.keySet()) {
            if (newSubtask.getEpicId() == epicId) {
                int subtaskId= addId();
                newSubtask.setId(subtaskId);
                subtasks.put(subtaskId, newSubtask);
                epics.get(epicId).getSubtasksId().add(subtaskId);
                changeStatus(epicId);
            }
        }
    }

    public void addEpic(Epic newEpic) {
        int id = addId();
        newEpic.setId(id);
        epics.put(id, newEpic);
    }

    public void changeStatus(int epicId) {
        int curSubtasks = 0;
        int done = 0;
        int news = 0;
        for (Integer subtaskId : epics.get(epicId).getSubtasksId()) {
                curSubtasks++;
                if (subtasks.get(subtaskId).getStatus().equals("DONE")) {
                    done++;
                } else if (subtasks.get(subtaskId).getStatus().equals("NEW")) {
                    news++;
                }
        } if (done == curSubtasks) {
            epics.get(epicId).setStatus("DONE");
        } else if (news == curSubtasks) {
            epics.get(epicId).setStatus("NEW");
        } else {
            epics.get(epicId).setStatus("IN_PROGRESS");
        }
    }

    //методы получения по id
    public Epic findEpic(int id) {
        return epics.get(id);
    }

    public Subtask findSubtask(int id) {
        return subtasks.get(id);
    }
    public Task findTask(int id) {
        return tasks.get(id);
    }

    //методы получения списков задач
    public ArrayList<Task> getAllTasks() {
        ArrayList<Task> tasksList = new ArrayList<>();
        for (Task task : tasks.values()) {
            tasksList.add(task);
        } return tasksList;
    }

    public ArrayList<Epic> getAllEpics() {
        ArrayList<Epic> epicsList = new ArrayList<>();
        for (Epic epic : epics.values()) {
            epicsList.add(epic);
        } return epicsList;
    }

    public ArrayList<Subtask> getAllSubtasks() {
        ArrayList<Subtask> subtasksList = new ArrayList<>();
        for (Subtask subtask : subtasks.values()) {
            subtasksList.add(subtask);
        } return subtasksList;
    }

    //методы удаления по id
    public void removeTask(Integer id) {
        tasks.remove(id);
    }

    public void removeEpic(Integer id) {
        for (Integer subtaskId : epics.get(id).getSubtasksId()) {
            subtasks.remove(subtaskId);
        } epics.remove(id);
    }

    public void removeSubtask(Integer id) {
        Integer epicId = subtasks.get(id).getEpicId();
        epics.get(epicId).getSubtasksId().remove(id);
    }

    //методы удаления всех задач
    public void removeAllTasks() {
        tasks.clear();
    }

    public void removeAllEpics() {
        epics.clear();
        subtasks.clear();
    }

    public void removeAllSubtasks() {
        subtasks.clear();
        for (Epic epic : epics.values()) {
            epic.getSubtasksId().clear();
            changeStatus(epic.getId());
        }
    }

    //методы обновления задач
    public void updateTask(Task newTask) {
        tasks.put(newTask.getId(), newTask);
    }

    public void updateSubtask(Subtask newSubtask) {
        subtasks.put(newSubtask.getId(), newSubtask);
        changeStatus(newSubtask.getEpicId());
    }

    public void updateEpic(Epic newEpic) {
        epics.put(newEpic.getId(), newEpic);
    }

    //получение списка всех подзадач epica
    public ArrayList<Integer> printEpicSubtasks(int id) {
        return epics.get(id).getSubtasksId();
    }
}