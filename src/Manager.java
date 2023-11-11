import java.util.ArrayList;
import java.util.HashMap;

public class Manager {
    static int count = 0;

    public static int addId() {
        count++;
        return count;
    }

    public static void removeId() {
        count--;
    }

    public void findTask(HashMap<Epic, ArrayList<Subtask>> epics, ArrayList<Task> tasks, int id) {
        for (Task task : tasks) {
            if (task.id == id) {
                System.out.println("Найдена задача: " + task.goal);
            }
        } for (Epic epic : epics.keySet()) {
            if (epic.id == id) {
                System.out.println("Найден Epic: " + epic.goal);
            }
        } for (ArrayList<Subtask> subtasks : epics.values()) {
            for (Subtask subtask : subtasks) {
                if (subtask.id == id) {
                    System.out.println("Найдена подзадача: " + subtask.goal);
                }
            }
        }
    }

    public void printAllTasks(HashMap<Epic, ArrayList<Subtask>> epics, ArrayList<Task> tasks) {
        System.out.println("Все Epic'и: ");
        for (Epic epic : epics.keySet()) {
            System.out.println("---" + epic.goal + "---");
            ArrayList<Subtask> subtasks = epics.get(epic);
            for (Subtask subtask : subtasks) {
                System.out.println(subtask.goal);
            }
        }

        System.out.println("Все одиночные задачи:");
        for (Task task : tasks) {
            System.out.println(task.goal);
        }
    }
}