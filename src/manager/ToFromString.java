package manager;

import tasks.Epic;
import tasks.Status;
import tasks.Subtask;
import tasks.Task;

import java.time.LocalDateTime;
import java.time.Duration;

public class ToFromString {
    public static String toString(Task task) {
        if (task.getClass() == Subtask.class) {
            return task.getId() + "," + "Subtask" +  "," + task.getName() + "," + task.getStatus() + ","
                    + task.getDescription() + "," + ((Subtask) task).getEpicId() + "," + task.getStartTime() + ","
                    + task.getDuration() + "," + task.getEndTime() + "\n";
        } else if (task.getClass() == Epic.class){
            return task.getId() + "," + "Epic" +  ","+ task.getName() + "," + task.getStatus() + "," + task.getDescription()
                    + "," + task.getStartTime() + "," + task.getDuration() + "," + task.getEndTime()+ "\n";
        } else {
            return task.getId() + "," + "Task" + ","+ task.getName() + "," + task.getStatus() + ","
                    + task.getDescription() + "," + task.getStartTime() + "," + task.getDuration() + ","
                    + task.getEndTime() + "\n";
        }
    }

    public static Task fromString(String value) {
        String[] values = value.split(",");
        int id = Integer.parseInt(values[0]);
        String type = values[1];
        String name = values[2];
        String description = values[4];
        String epicId = null;
        String startTime = values[6];
        String duration = values[7];
        String endTime = values[8];
        if (values.length == 6) {
            epicId = values[5];
        }
        if (type.equals("Subtask")) {
            Subtask subtask = new Subtask(name, description, Integer.parseInt(epicId));
            subtask.setId(id);
            subtask.setStatus(Status.valueOf(values[3]));
            subtask.setStartTime(LocalDateTime.parse(startTime));
            subtask.setDuration(Duration.parse(duration));
            subtask.setEndTime(LocalDateTime.parse(endTime));
            return subtask;
        } else if (type.equals("Epic")){
            Epic epic = new Epic(name, description);
            epic.setId(id);
            epic.setStatus(Status.valueOf(values[3]));
            epic.setStartTime(LocalDateTime.parse(startTime));
            epic.setDuration(Duration.parse(duration));
            epic.setEndTime(LocalDateTime.parse(endTime));
            return epic;
        } else {
            Task task = new Task(name, description);
            task.setId(id);
            task.setStatus(Status.valueOf(values[3]));
            task.setStartTime(LocalDateTime.parse(startTime));
            task.setDuration(Duration.parse(duration));
            task.setEndTime(LocalDateTime.parse(endTime));
            return task;
        }
    }
}
