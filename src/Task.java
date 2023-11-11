import java.util.ArrayList;

public class Task {
    String status;
    String goal;
    int id;
    Task(int id, String goal) {
        this.id = id;
        this.status = "NEW";
        this.goal = goal;
    }

    public void addTask(ArrayList<Task> tasks, String goal) {
        int id = Manager.addId();
        Task newTask = new Task(id, goal);
        tasks.add(newTask);
    }

    public void removeTask(ArrayList<Task> tasks, int id) {
        for (Task task : tasks) {
            if (task.id == id) {
                tasks.remove(task);
                Manager.removeId();
            }
        }
    }

    public void makeTask(ArrayList<Task> tasks, int id) {
        for (Task task : tasks) {
            if (task.id == id) {
                task.status = "DONE";
            }
        }
    }
}
