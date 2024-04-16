package manager;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
        if (values[1].equals("SUBTASK")) {
            Subtask subtask = new Subtask(values[0], values[3], Integer.parseInt(values[5]));
            subtask.setName(values[2]);
            subtask.setDescription(values[4]);
            return subtask;
        } else if (values[1].equals("EPIC")){
            Epic epic = new Epic(values[0], values[3]);
            epic.setName(values[2]);
            epic.setDescription(values[4]);
            return epic;
        } else {
            Task task = new Task(values[0], values[3]);
            task.setName(values[2]);
            task.setDescription(values[4]);
            return task;
        }
    }

    static List<Task> historyFromString(String value) throws IOException {
        List<Task> history = new ArrayList<>();
        FileReader reader = new FileReader(value);
        BufferedReader br = new BufferedReader(reader);
        while (br.ready()) {
            String str = br.readLine();
            Task task = fromString(str);
            history.add(task);
        }
        br.close();
        return history;
    }
}
