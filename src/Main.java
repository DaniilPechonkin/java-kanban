import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Manager manager = new Manager();
        Task task = new Task(0, "0");
        Epic epic = new Epic(0, "0");
        HashMap<Epic, ArrayList<Subtask>> epics = new HashMap<>();
        ArrayList<Task> tasks = new ArrayList<>();

        while (true) {
            printMenu();
            int i = scanner.nextInt();
            if (i == 1) {
                System.out.println("Введите задачу");
                String newTask = scanner.next();
                task.addTask(tasks, newTask);
            } else if (i == 2) {
                System.out.println("Введите названия Epica");
                String newEpic = scanner.next();
                epic.createEpic(epics, newEpic);
            } else if (i == 3) {
                System.out.println("Введите номер Epica");
                int id = scanner.nextInt();
                System.out.println("Введите название подзадачи");
                String newSubtask = scanner.next();
                epic.addSubtask(epics, id, newSubtask);
                epic.addSubtask(epics, id, newSubtask);
            } else if (i == 4) {
                System.out.println("Введите id:");
                int id = scanner.nextInt();
                task.removeTask(tasks, id);
            } else if (i == 5) {
                System.out.println("Введите id:");
                int id = scanner.nextInt();
                epic.removeEpic(epics, id);
            } else if (i == 6) {
                System.out.println("Введите id:");
                int id = scanner.nextInt();
                epic.removeSubtask(epics, id);
            } else if (i == 7) {
                System.out.println("Введите id:");
                int id = scanner.nextInt();
                manager.findTask(epics, tasks, id);
            } else if (i == 8) {
                System.out.println("Введите id:");
                int id = scanner.nextInt();
                task.makeTask(tasks, id);
            } else if (i == 9) {
                System.out.println("Введите id:");
                int id = scanner.nextInt();
                epic.makeSubtask(epics, id);
            } else if (i == 10) {
                System.out.println("Введите id:");
                int id = scanner.nextInt();
                epics = epic.makeEpic(epics, id);
            } else if (i == 11) {
                manager.printAllTasks(epics, tasks);
            } else if (i == 0) {
                System.out.println("Завершение работы.");
                break;
            } else {
                System.out.println("Такой команды нет.");
            }
        }
    }

    public static void printMenu() {
        System.out.println("Список доступных комманд:");
        System.out.println("1 - создать задачу.");
        System.out.println("2 - создать Epic");
        System.out.println("3 - создать подзадачу");
        System.out.println("4 - удалить задачу");
        System.out.println("5 - удалить Epic");
        System.out.println("6 - удалить подзадачу");
        System.out.println("7 - показать задачу под определенным id");
        System.out.println("8 - отметить выполненную задачу");
        System.out.println("9 - отметить выполненную подзадачу");
        System.out.println("10 - отметить выполненный epic");
        System.out.println("11 - вывести список всех задач");
        System.out.println("0 - завершить работу");
    }
}
