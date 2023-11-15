package manager;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.HashMap;

public class Manager {
    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();
    private final HashMap<Integer, Subtask> subtasks = new HashMap<>();
    int count = 0;

    public int addId() {
        return ++count;
    }

    //методы создания
    public void addTask(String name, String description) {
        int id = addId();
        Task newTask = new Task(name, description);
        newTask.setId(id);
        tasks.put(id, newTask);
    }

    public void addSubtask(int id, String name, String description) {
        for (Integer epicId : epics.keySet()) {
            if (id == epicId) {
                int subtaskId= addId();
                Subtask newSubtask = new Subtask(name, description, epicId);
                newSubtask.setId(subtaskId);
                subtasks.put(subtaskId, newSubtask);
                epics.get(epicId).getSubtasksId().add(subtaskId);

                int curSubtasks = 0;
                int done = 0;
                int news = 0;
                for (Subtask subtask : subtasks.values()) {
                    if (subtask.getId() == epicId) {
                        curSubtasks++;
                        if (subtask.getStatus().equals("DONE")) {
                            done++;
                        } else if (subtask.getStatus().equals("NEW")) {
                            news++;
                        }
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
    }

    public void addEpic(String name, String description) {
        int id = addId();
        Epic newEpic = new Epic(name, description);
        newEpic.setId(id);
        epics.put(id, newEpic);
    }

    //методы получения по id
    public void findEpic(int id) {
        for (Integer epicId : epics.keySet()) {
            if (epicId == id) {
                System.out.println("Найден tasks.Epic: " + epics.get(epicId).getDescription());
            }
        }
    }

    public void findSubtask(int id) {
        for (Integer subtaskId : subtasks.keySet()) {
            if (subtaskId == id) {
                System.out.println("Найдена подзадача: " + subtasks.get(subtaskId).getDescription());
            }
        }
    }
    public void findTask(int id) {
        for (Integer taskId : tasks.keySet()) {
            if (taskId == id) {
                System.out.println("Найдена задача: " + tasks.get(taskId).getDescription());
            }
        }
    }

    //методы получения списков задач
    public void printAllTasks() {
        System.out.println("Все одиночные задачи:");
        for (Task task : tasks.values()) {
            System.out.println(task.getId() + ". " + task.getName() + " : " + task.getDescription() + " - "
                    + task.getStatus());
        }
    }

    public void printAllEpics() {
        for (Epic epic : epics.values()) {
            System.out.println(epic.getId() + ". ---" + epic.getName() + " : " + epic.getDescription() +" - "
                    + epic.getStatus() + " ---");
            for (Subtask subtask : subtasks.values()) {
                if (epic.getId() == subtask.getEpicId()) {
                    System.out.println(subtask.getId() + ". " + subtask.getName() + " : " + subtask.getDescription()
                    + " - " + subtask.getStatus());
                }
            }
        }
    }

    public void printAllSubtasks() {
        for (Subtask subtask : subtasks.values()) {
            Epic epic = epics.get(subtask.getEpicId());
            System.out.println(subtask.getId() + ". " + subtask.getName() + " : " + subtask.getDescription() +
                    " - " + subtask.getStatus());
            System.out.println("Соответствует Epic'y - " + epic.getName() + " с id " + epic.getId());
            }
    }

    //методы удаления по id
    public void removeTask(int id) {
        for (Integer taskId : tasks.keySet()) {
            if (taskId == id) {
                tasks.remove(taskId);
                break;
            }
        }
    }

    public void removeEpic(int id) {
        for (Integer epicId : epics.keySet()) {
            if (epicId == id) {
                for (Subtask subtask : subtasks.values()) {
                    if (subtask.getEpicId() == epicId) {
                        int subtaskId = subtask.getId();
                        subtasks.remove(subtaskId);
                    }
                } epics.remove(epicId);
                break;
            }
        }
    }

    public void removeSubtask(int id) {
        for (Integer subtaskId : subtasks.keySet()) {
            if (subtaskId == id) {
                for (Epic epic : epics.values()) {
                    if (epic.getId() == subtaskId) {
                       epic.getSubtasksId().remove(subtaskId);
                    }
                }
                subtasks.remove(subtaskId);
                break;
            }
        }
    }

    //методы удаления всех задач
    public void removeAllTasks() {
        tasks.clear();
    }

    public void removeAllEpics() {
        epics.clear();
    }

    public void removeAllSubtasks() {
        subtasks.clear();
    }

    //методы обновления задач
    public void updateTask(int id, String name, String description, String status) {
        Task newTask = new Task(name, description);
        newTask.setId(id);
        newTask.setStatus(status);
        tasks.put(id, newTask);
    }

    public void updateSubtask(int id, String name, String description, String status) {
        for (Integer epicId : epics.keySet()) {
            if (id == epicId) {
                int subtaskId= addId();
                Subtask newSubtask = new Subtask(name, description, epicId);
                newSubtask.setId(subtaskId);
                newSubtask.setStatus(status);
                subtasks.put(subtaskId, newSubtask);
                epics.get(epicId).getSubtasksId().add(subtaskId);

                int curSubtasks = 0;
                int done = 0;
                int news = 0;
                for (Subtask subtask : subtasks.values()) {
                    if (subtask.getId() == epicId) {
                        curSubtasks++;
                        if (subtask.getStatus().equals("DONE")) {
                            done++;
                        } else if (subtask.getStatus().equals("NEW")) {
                            news++;
                        }
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
    }

    public void updateEpic(int id, String name, String description, String status) {
        Epic newEpic = new Epic(name, description);
        newEpic.setId(id);
        newEpic.setStatus(status);
        epics.put(id, newEpic);
        if (newEpic.getStatus().equals("DONE")) {
            for (Subtask subtask : subtasks.values()) {
                if (subtask.getEpicId() == id) {
                    subtask.setStatus("DONE");
                }
            }
        }
    }

    //получение списка всех подзадач epica
    public void printEpicSubtasks(int id) {
        Epic epic = epics.get(id);
        System.out.println("---" + epic.getName() + " : " + epic.getDescription());
        for (Subtask subtask : subtasks.values()) {
            if (subtask.getEpicId() == id) {
                System.out.println(subtask.getId() + ". " + subtask.getName() + " : " + subtask.getDescription());
            }
        }
    }
}