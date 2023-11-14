package TasksClasses;

public class Subtask extends Task{
    public int getEpicId() {
        return epicId;
    }
    int epicId;

    public Subtask(int id, String name, String description, int epicId) {
        super(id, name, description);
        this.epicId = epicId;
    }
}