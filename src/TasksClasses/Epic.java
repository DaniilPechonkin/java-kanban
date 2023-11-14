package TasksClasses;

import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<Integer> subtasksId = new ArrayList<>();
    public Epic(int id, String name, String description) {
        super(id, name, description);
    }

    public ArrayList<Integer> getSubtasksId() {
        return subtasksId;
    }
}