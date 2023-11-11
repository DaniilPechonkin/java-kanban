import java.util.HashMap;
import java.util.ArrayList;

public class Epic extends Task {
    Epic(int id, String goal) {
        super(id, goal);
    }

    public void createEpic(HashMap<Epic, ArrayList<Subtask>> epics, String epicGoal) {
        int id = Manager.addId();
        Epic newEpic = new Epic(id, epicGoal);
        ArrayList<Subtask> subtasks = new ArrayList<>();
        epics.put(newEpic, subtasks);
    }

    public void addSubtask(HashMap<Epic, ArrayList<Subtask>> epics, int curId, String goal) {
        for (Epic epic : epics.keySet()) {
            if (epic.id == curId) {
                int id = Manager.addId();
                ArrayList<Subtask> subtasks = epics.get(epic);
                System.out.println(subtasks);
                Subtask newSubtask = new Subtask(id, goal);
                subtasks.add(newSubtask);
                epics.put(epic, subtasks);
            }
        }
    }

    public void removeEpic(HashMap<Epic, ArrayList<Subtask>> epics, int id) {
        for (Epic epic : epics.keySet()) {
            if (epic.id == id) {
                epics.remove(epic);
                Manager.removeId();
            }
        }
    }

    public void removeSubtask(HashMap<Epic, ArrayList<Subtask>> epics, int id) {
        for (ArrayList<Subtask> subtasks : epics.values()) {
            for (Subtask subtask : subtasks) {
                if (subtask.id == id) {
                    subtasks.remove(subtask);
                    Manager.removeId();
                }
            }
        }
    }

    public void makeSubtask(HashMap<Epic, ArrayList<Subtask>> epics, int id) {
        ArrayList<Subtask> curSubtasks = new ArrayList<>();
        Epic curEpic = new Epic(0, "0");
        for (ArrayList<Subtask> subtasks : epics.values()) {
            for (Subtask subtask : subtasks) {
                if (subtask.id == id) {
                    subtask.status = "DONE";
                    curSubtasks = subtasks;
                    for (Epic epic : epics.keySet()) {
                        if (epics.get(epic) == curSubtasks) {
                            curEpic = epic;
                        }
                    }
                }
            }
        } int done = 0;
        int news= 0;
        for (Subtask subtask : curSubtasks) {
            if (subtask.status.equals("DONE")) {
                done++;
            } else if(subtask.status.equals("NEW")) {
                news++;
            }
        }
        if (done == curSubtasks.size()) {
            curEpic.status = "DONE";
        } else if (news == curSubtasks.size()) {
            curEpic.status = "NEW";
        } else {
            curEpic.status = "IN_PROCESS";
        }
    }

    public HashMap<Epic, ArrayList<Subtask>> makeEpic(HashMap<Epic, ArrayList<Subtask>> epics, int id) {
        for (Epic epic : epics.keySet()) {
            if (epic.id == id) {
                epic.status = "DONE";
                ArrayList<Subtask> curSubtasks = epics.get(epic);
                for (Subtask subtask : curSubtasks) {
                    subtask.status = "DONE";
                } epics.put(epic, curSubtasks);
            }
        } return epics;
    }

}