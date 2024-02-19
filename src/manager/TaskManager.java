package manager;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.ArrayList;
import java.util.List;

public interface TaskManager {
    //методы создания
    void addTask(Task newTask);

    void addSubtask(Subtask newSubtask);

    void addEpic(Epic newEpic);

    //методы получения по id
    Epic findEpic(int id);

    Subtask findSubtask(int id);

    Task findTask(int id);

    //методы получения списков задач
    ArrayList<Task> getAllTasks();

    ArrayList<Epic> getAllEpics();

    ArrayList<Subtask> getAllSubtasks();

    //методы удаления по id
    void removeTask(Integer id);

    void removeEpic(Integer id);

    void removeSubtask(Integer id);

    //методы удаления всех задач
    void removeAllTasks();

    void removeAllEpics();

    void removeAllSubtasks();

    //методы обновления задач
    void updateTask(Task newTask);

    void updateSubtask(Subtask newSubtask);

    void updateEpic(Epic newEpic);

    //получение списка всех подзадач epica
    ArrayList<Subtask> getEpicSubtasks(int id);

    List<Task> getHistory();
}