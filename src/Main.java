import manager.InMemoryHistoryManager;
import manager.InMemoryTaskManager;

public class Main {

    public static void main(String[] args) {
        InMemoryTaskManager manager = new InMemoryTaskManager();
        InMemoryHistoryManager historyManager = new InMemoryHistoryManager();

        tasks.Task task1 = new tasks.Task("firstTask", "1");
        tasks.Task task2 = new tasks.Task("secondTask", "2");
        manager.addTask(task1);
        manager.addTask(task2);

        tasks.Epic epic1 = new tasks.Epic("firstEpic", "123");
        manager.addEpic(epic1);
        tasks.Subtask subtask1 = new tasks.Subtask("firstSubtask", "123",3);
        tasks.Subtask subtask2 = new tasks.Subtask("secondSubtask", "123",3);
        manager.addSubtask(subtask1);
        manager.addSubtask(subtask2);

        tasks.Epic epic2 = new tasks.Epic("secondEpic", "123");
        manager.addEpic(epic2);
        tasks.Subtask subtask3 = new tasks.Subtask( "firstSubtask", "123", 3);
        manager.addSubtask(subtask3);

        System.out.println(manager.getAllTasks());
        System.out.println(manager.getAllEpics());
        System.out.println(manager.getAllSubtasks());

        manager.removeEpic(6);
        manager.removeTask(1);

        System.out.println(manager.getAllTasks());
        System.out.println(manager.getAllEpics());
        System.out.println(manager.getAllSubtasks());

        manager.findTask(0);
        manager.findTask(1);
        manager.findTask(2);

        System.out.println(historyManager.getHistory());
    }
}