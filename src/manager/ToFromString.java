package manager;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

public class ToFromString {
    public static String toString(Task task) {
        if (task.getClass() == Subtask.class) {
            return task.getId() + "," + task.getClass() + ","+ task.getName() + "," + task.getStatus() + ","
                    + task.getDescription() + "," + ((Subtask) task).getEpicId() + "\n";
        } else {
            return task.getId() + "," + task.getClass() + ","+ task.getName() + "," + task.getStatus() + "," + task.getDescription() + "\n";
        }
    }

    public static Task fromString(String value) {
        String[] values = value.split(",");
        String id = values[0];
        String type = values[1];
        String name = values[2];
        String status = values[3];
        String description = values[4];
        String epicId = null;
        if (values.length == 6) {
            epicId = values[5];
        }
        if (type.equals("SUBTASK")) {
            Subtask subtask = new Subtask(id, status, Integer.parseInt(epicId));
            subtask.setName(name);
            subtask.setDescription(description);
            return subtask;
        } else if (type.equals("EPIC")){
            Epic epic = new Epic(id, status);
            epic.setName(name);
            epic.setDescription(description);
            return epic;
        } else {
            Task task = new Task(id, status);
            task.setName(name);
            task.setDescription(description);
            return task;
        }
    }
}
