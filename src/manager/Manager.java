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

    //методы создания
    public void addTask(Task newTask) {
        int id = addId();
        newTask.setId(id);
        tasks.put(id, newTask);
    }

    public void addSubtask(Subtask newSubtask) {
        Epic epic = epics.get(newSubtask.getEpicId());
        int subtaskId= addId();
        newSubtask.setId(subtaskId);
        subtasks.put(subtaskId, newSubtask);
        epic.getSubtasksId().add(subtaskId);
        changeStatus(epic.getId());
    }

    public void addEpic(Epic newEpic) {
        int id = addId();
        newEpic.setId(id);
        epics.put(id, newEpic);
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
        return new ArrayList<>(tasks.values());
    }

    public ArrayList<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    public ArrayList<Subtask> getAllSubtasks() {
        return new ArrayList<>(subtasks.values());
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
        subtasks.remove(id);
        changeStatus(epicId);
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
    public ArrayList<Subtask> getEpicSubtasks(int id) {
        ArrayList<Subtask> subtasks = new ArrayList<>();
        ArrayList<Integer> subtasksId = epics.get(id).getSubtasksId();
        for (Integer subtaskId : subtasksId) {
            subtasks.add(subtasks.get(subtaskId));
        }
        return subtasks;
    }

    private int addId() {
        return ++count;
    }

    private void changeStatus(int epicId) {
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
}