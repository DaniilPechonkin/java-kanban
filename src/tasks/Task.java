package tasks;

public class Task {
    private String name;
    private String status;
    private String description;
    private int id;
    public Task(String name, String description) {
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

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(int id) {
        this.id = id;
    }
}