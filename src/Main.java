import manager.HistoryManager;
import manager.Manager;
import manager.TaskManager;
import tasks.Task;
import tasks.Epic;
import tasks.Subtask;

public class Main {

    public static void main(String[] args) {
        HistoryManager historyManager = Manager.getDefaultHistory();
        TaskManager taskManager = Manager.getDefaultTask();

        Task task1 = new tasks.Task("firstTask", "1");
        Task task2 = new tasks.Task("secondTask", "2");
        taskManager.addTask(task1);
        taskManager.addTask(task2);

        Epic epic1 = new tasks.Epic("firstEpic", "123");
        taskManager.addEpic(epic1);
        Subtask subtask1 = new tasks.Subtask("firstSubtask", "123",3);
        Subtask subtask2 = new tasks.Subtask("secondSubtask", "123",3);
        taskManager.addSubtask(subtask1);
        taskManager.addSubtask(subtask2);

        Epic epic2 = new tasks.Epic("secondEpic", "123");
        taskManager.addEpic(epic2);
        Subtask subtask3 = new tasks.Subtask( "firstSubtask", "123", 3);
        taskManager.addSubtask(subtask3);

        System.out.println(taskManager.getAllTasks());
        System.out.println(taskManager.getAllEpics());
        System.out.println(taskManager.getAllSubtasks());

        taskManager.removeEpic(6);
        taskManager.removeTask(1);

        System.out.println(taskManager.getAllTasks());
        System.out.println(taskManager.getAllEpics());
        System.out.println(taskManager.getAllSubtasks());

        taskManager.findTask(1);
        taskManager.findTask(2);
        taskManager.findTask(3);

        System.out.println(historyManager.getHistory());
    }
}