package TasksClasses;

public class Task {
    private String name;
    private String status;
    private String description;
    private int id;
    public Task(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.status = "NEW";
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}