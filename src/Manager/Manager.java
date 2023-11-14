package Manager;

import TasksClasses.Epic;
import TasksClasses.Subtask;
import TasksClasses.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Manager {
    Scanner scanner = new Scanner(System.in);
    ArrayList<Task> tasks = new ArrayList<>();
    ArrayList<Epic> epics = new ArrayList<>();
    ArrayList<Subtask> subtasks = new ArrayList<>();
    int count = 0;

    public int addId() {
        return ++count;
    }

    public int removeId() {
        return --count;
    }

    public void addTask() {
        int id = addId();
        System.out.println("Введите название задачи: ");
        String name = scanner.next();
        System.out.println("Введите описание задачи: ");
        String description = scanner.next();
        Task newTask = new Task(id, name, description);
        tasks.add(newTask);
    }

    public void removeTask(int id) {
        for (Task task : tasks) {
            if (task.getId() == id) {
                tasks.remove(task);
                removeId();
            }
        }
    }

    public void makeTask(ArrayList<Task> tasks, int id) {
        for (Task task : tasks) {
            if (task.getId() == id) {
                task.setStatus("DONE");
            }
        }
    }
    public void findTask(HashMap<Epic, ArrayList<Subtask>> epics, ArrayList<Task> tasks, int id) {
        for (Task task : tasks) {
            if (task.getId() == id) {
                System.out.println("Найдена задача: " + task.getDescription());
            }
        } for (Epic epic : epics.keySet()) {
            if (epic.getId() == id) {
                System.out.println("Найден TasksClasses.Epic: " + epic.getDescription());
            }
        } for (ArrayList<Subtask> subtasks : epics.values()) {
            for (Subtask subtask : subtasks) {
                if (subtask.getId() == id) {
                    System.out.println("Найдена подзадача: " + subtask.getDescription());
                }
            }
        }
    }

    public void printAllTasks() {
        System.out.println("Все Epic'и: ");
        for (Epic epic : epics) {
            System.out.println("---" + epic.getName() + " : " + epic.getDescription() + "---");
            for (Subtask subtask : subtasks) {
                if (epic.getId() == subtask.getEpicId()) {
                    System.out.println(subtask.getName() + " : " + subtask.getDescription());
                }
            }
        }

        System.out.println("Все одиночные задачи:");
        for (Task task : tasks) {
            System.out.println(task.getName() + " : " + task.getDescription());
        }
    }

    public void addSubtask() {
        System.out.println("Введите id Epica, в который нужно добавить подзадачу:");
        int epicId = scanner.nextInt();
        for (Epic epic : epics) {
            if (epic.getId() == epicId) {
                System.out.println("Введите название задачи: ");
                String name = scanner.next();
                System.out.println("Введите описание задачи: ");
                String description = scanner.next();
                int id = addId();
                Subtask newSubtask = new Subtask(id, name, description, epicId);
                subtasks.add(newSubtask);
                epic.getSubtasksId().add(newSubtask.getId());

                int curSubtasks = 0;
                int done = 0;
                int news = 0;
                for (Subtask subtask : subtasks) {
                    if (subtask.getId() == epic.getId()) {
                        curSubtasks++;
                        if (subtask.getStatus().equals("DONE")) {
                            done++;
                        } else if (subtask.getStatus().equals("NEW")) {
                            news++;
                        }
                    }
                } if (done == curSubtasks) {
                    epic.setStatus("DONE");
                } else if (news == curSubtasks) {
                    epic.setStatus("NEW");
                } else {
                    epic.setStatus("IN_PROGRESS");
                }
            }
        }
    }

    public void addEpic() {
        int id = addId();
        System.out.println("Введите название задачи: ");
        String name = scanner.next();
        System.out.println("Введите описание задачи: ");
        String description = scanner.next();
        Epic newEpic = new Epic(id, name, description);
        epics.add(newEpic);
    }

    public void removeEpic() {
        System.out.println("Введите id Epica, который нужно удалить:");
        int epicId = scanner.nextInt();
        for (Epic epic : epics) {
            if (epicId == epic.getId()) {
                for (Subtask subtask : subtasks) {
                    if (subtask.getEpicId() == epicId) {
                        subtasks.remove(subtask);
                        removeId();
                    }
                } epics.remove(epic);
                break;
            }
        }
    }

    public void removeSubtask() {
        System.out.println("Введите id подзадачи, которую нужно удалить:");
        int subtaskId = scanner.nextInt();
        for (Subtask subtask : subtasks) {
            if (subtaskId == subtask.getId()) {
                for (Epic epic : epics) {
                    if (epic.getId() == subtask.getEpicId()) {
                       epic.getSubtasksId().remove(subtaskId);
                       removeId();
                    }
                }
                subtasks.remove(subtask);
                break;
            }
        }
    }
}