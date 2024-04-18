package manager;

import tasks.Epic;
import tasks.Status;
import tasks.Subtask;
import tasks.Task;

public class ToFromString {
    static String EPIC_IN_FILE = "class tasks.Epic";
    static String SUBTASK_IN_FILE = "class tasks.Subtask";

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
        int id = Integer.parseInt(values[0]);
        String type = values[1];
        String name = values[2];
        String status = values[3];
        String description = values[4];
        String epicId = null;
        if (values.length == 6) {
            epicId = values[5];
        }
        if (type.equals(SUBTASK_IN_FILE)) {
            Subtask subtask = new Subtask(name, description, Integer.parseInt(epicId));
            subtask.setId(id);
            subtask.setStatus(getStatus(status));
            return subtask;
        } else if (type.equals(EPIC_IN_FILE)){
            Epic epic = new Epic(name, description);
            epic.setId(id);
            epic.setStatus(getStatus(status));
            return epic;
        } else {
            Task task = new Task(name, description);
            task.setId(id);
            task.setStatus(getStatus(status));
            return task;
        }
    }

    public static Status getStatus(String str) {
        if (str.equals("NEW")) {
            return Status.NEW;
        } else if (str.equals("DONE")) {
            return Status.DONE;
        } else {
            return Status.IN_PROGRESS;
        }
    }
}
