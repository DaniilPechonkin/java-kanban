package manager;

import tasks.Epic;
import tasks.Status;
import tasks.Subtask;
import tasks.Task;

public class ToFromString {
    public static String toString(Task task) {
        if (task.getClass() == Subtask.class) {
            return task.getId() + "," + "Subtask" +  "," + task.getName() + "," + task.getStatus() + ","
                    + task.getDescription() + "," + ((Subtask) task).getEpicId() + "\n";
        } else if (task.getClass() == Epic.class){
            return task.getId() + "," + "Epic" +  ","+ task.getName() + "," + task.getStatus() + "," + task.getDescription() + "\n";
        } else {
            return task.getId() + "," + "Task" + ","+ task.getName() + "," + task.getStatus() + "," + task.getDescription() + "\n";
        }
    }

    public static Task fromString(String value) {
        String[] values = value.split(",");
        int id = Integer.parseInt(values[0]);
        String type = values[1];
        String name = values[2];
        String description = values[4];
        String epicId = null;
        if (values.length == 6) {
            epicId = values[5];
        }
        if (type.equals("Subtask")) {
            Subtask subtask = new Subtask(name, description, Integer.parseInt(epicId));
            subtask.setId(id);
            subtask.setStatus(Status.valueOf(values[3]));
            return subtask;
        } else if (type.equals("Epic")){
            Epic epic = new Epic(name, description);
            epic.setId(id);
            epic.setStatus(Status.valueOf(values[3]));
            return epic;
        } else {
            Task task = new Task(name, description);
            task.setId(id);
            task.setStatus(Status.valueOf(values[3]));
            return task;
        }
    }
}
