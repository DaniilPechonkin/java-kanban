package tasks;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private ArrayList<Integer> subtasksId = new ArrayList<>();
    private LocalDateTime endTime;

    public Epic(String name, String description) {
        super(name, description);
    }

    public ArrayList<Integer> getSubtasksId() {
        return subtasksId;
    }

    public void setSubtasksId(ArrayList<Integer> subtasksId) {
        this.subtasksId = subtasksId;
    }

    public void getEndTime(List<Subtask> subtasks) {
        LocalDateTime latestEndTime = null;
        for (Subtask subtask : subtasks) {
            LocalDateTime subtaskEndTime = subtask.getEndTime();
            if (latestEndTime == null || (subtaskEndTime != null && subtaskEndTime.isAfter(latestEndTime))) {
                latestEndTime = subtaskEndTime;
            }
        }
        this.endTime = latestEndTime;
    }
}