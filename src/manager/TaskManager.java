package manager;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.ArrayList;
import java.util.LinkedList;

public interface TaskManager {

    //методы создания
    public void addTask(Task newTask);

    public void addSubtask(Subtask newSubtask);

    public void addEpic(Epic newEpic);

    //методы получения по id
    public Epic findEpic(int id);

    public Subtask findSubtask(int id);
    public Task findTask(int id);

    //методы получения списков задач
    public ArrayList<Task> getAllTasks();

    public ArrayList<Epic> getAllEpics();

    public ArrayList<Subtask> getAllSubtasks();

    //методы удаления по id
    public void removeTask(Integer id);

    public void removeEpic(Integer id);

    public void removeSubtask(Integer id);

    //методы удаления всех задач
    public void removeAllTasks();

    public void removeAllEpics();

    public void removeAllSubtasks();

    //методы обновления задач
    public void updateTask(Task newTask);

    public void updateSubtask(Subtask newSubtask);

    public void updateEpic(Epic newEpic);

    //получение списка всех подзадач epica
    public ArrayList<Subtask> getEpicSubtasks(int id);

    public LinkedList<Task> getHistory();
}
