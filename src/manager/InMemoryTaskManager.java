package manager;

import tasks.Epic;
import tasks.Status;
import tasks.Subtask;
import tasks.Task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class InMemoryTaskManager implements TaskManager {
    private final HistoryManager historyManager = Manager.getDefaultHistory();
    protected final HashMap<Integer, Task> tasks = new HashMap<>();
    protected final HashMap<Integer, Epic> epics = new HashMap<>();
    protected final HashMap<Integer, Subtask> subtasks = new HashMap<>();
    TreeSet<Task> allTasks;
    private int count = 0;

    public InMemoryTaskManager() {
        Comparator<Task> compareByStartTime = Comparator.comparing((Task::getStartTime));
        this.allTasks = new TreeSet<>(compareByStartTime);
    }

    //методы создания
    @Override
    public void addTask(Task newTask) {
        if (isTasksCross(newTask)) {
            int id = addId();
            newTask.setId(id);
            tasks.put(id, newTask);
        }
    }

    @Override
    public void addSubtask(Subtask newSubtask) {
        if (isTasksCross(newSubtask)) {
            Epic epic = epics.get(newSubtask.getEpicId());
            int subtaskId = addId();
            newSubtask.setId(subtaskId);
            subtasks.put(subtaskId, newSubtask);
            epic.getSubtasksId().add(subtaskId);
            changeStatus(epic.getId());
        }
    }

    @Override
    public void addEpic(Epic newEpic) {
        if (isTasksCross(newEpic)) {
            int id = addId();
            newEpic.setId(id);
            epics.put(id, newEpic);
        }
    }

    //методы получения по id
    @Override
    public Epic findEpic(int id) {
        Epic epic = epics.get(id);
        historyManager.addInHistory(epic);
        return epic;
    }

    @Override
    public Subtask findSubtask(int id) {
        Subtask subtask = subtasks.get(id);
        historyManager.addInHistory(subtask);
        return subtask;
    }

    @Override
    public Task findTask(int id) {
        Task task = tasks.get(id);
        historyManager.addInHistory(task);
        return task;
    }

    @Override
    //методы получения списков задач
    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public ArrayList<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public ArrayList<Subtask> getAllSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    //методы удаления по id
    @Override
    public void removeTask(Integer id) {
        tasks.remove(id);
        historyManager.remove(id);
    }

    @Override
    public void removeEpic(Integer id) {
        Epic epic = epics.get(id);
        epic.getSubtasksId().stream()
                        .forEach(subtaskId -> {
                            subtasks.remove(subtaskId);
                            historyManager.remove(subtaskId);
                        });
        epics.remove(id);
        historyManager.remove(id);
    }

    @Override
    public void removeSubtask(Integer id) {
        int epicId = subtasks.get(id).getEpicId();
        epics.get(epicId).getSubtasksId().remove(id);
        subtasks.remove(id);
        historyManager.remove(id);
        changeStatus(epicId);
    }

    //методы удаления всех задач
    @Override
    public void removeAllTasks() {
        tasks.clear();
    }

    @Override
    public void removeAllEpics() {
        epics.clear();
        subtasks.clear();
    }

    @Override
    public void removeAllSubtasks() {
        subtasks.clear();
        epics.values().stream()
                        .forEach(epic -> {
                            epic.getSubtasksId().clear();
                            changeStatus(epic.getId());
                        });
    }

    //методы обновления задач
    @Override
    public void updateTask(Task newTask) {
        tasks.put(newTask.getId(), newTask);
    }

    @Override
    public void updateSubtask(Subtask newSubtask) {
        subtasks.put(newSubtask.getId(), newSubtask);
        changeStatus(newSubtask.getEpicId());
    }

    @Override
    public void updateEpic(Epic newEpic) {
        epics.put(newEpic.getId(), newEpic);
    }

    //получение списка всех подзадач epica
    @Override
    public ArrayList<Subtask> getEpicSubtasks(int id) {
        ArrayList<Integer> subtasksId = epics.get(id).getSubtasksId();
        return subtasksId.stream()
                .map(subtasks::get)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    private int addId() {
        return ++count;
    }

    private void changeStatus(int epicId) {
        int curSubtasks = 0;
        int done = 0;
        int news = 0;
        for (Integer subtaskId : epics.get(epicId).getSubtasksId()) {
            curSubtasks++;
            if (subtasks.get(subtaskId).getStatus().equals(Status.DONE)) {
                done++;
            } else if (subtasks.get(subtaskId).getStatus().equals(Status.NEW)) {
                news++;
            }
        }

        if (done == curSubtasks) {
            epics.get(epicId).setStatus(Status.DONE);
        } else if (news == curSubtasks) {
            epics.get(epicId).setStatus(Status.NEW);
        } else {
            epics.get(epicId).setStatus(Status.IN_PROGRESS);
        }
    }

    public HistoryManager getHistoryManager() {
        return historyManager;
    }

    public Duration getEpicDuration(Integer epicId) {
        LocalDateTime curStartTime = LocalDateTime.now();
        LocalDateTime curEndTime = LocalDateTime.MIN;
        for (Subtask subtask : subtasks.values()) {
            if (subtask.getEpicId() == epicId) {
                if (subtask.getStartTime().isBefore(curStartTime)) {
                    curStartTime = subtask.getStartTime();
                }
                if (subtask.getEndTime().isAfter(curEndTime)) {
                    curEndTime = subtask.getEndTime();
                }
            }
        }
        return Duration.between(curStartTime, curEndTime);
    }

    public LocalDateTime getEndTime(Integer epicId) {
        LocalDateTime curEndTime = LocalDateTime.MIN;
        for (Subtask subtask : subtasks.values()) {
            if (subtask.getEpicId() == epicId) {
                if (subtask.getEndTime().isAfter(curEndTime)) {
                    curEndTime = subtask.getEndTime();
                }
            }
        }
        return curEndTime;
    }

    public Set<Task> getPrioritizedTasks() {
        return allTasks;
    }

    public boolean isTasksCross(Task newTask) {
        Task taskBefore = allTasks.floor(newTask);
        if (taskBefore != null && !taskBefore.getEndTime().isBefore(newTask.getStartTime())) {
            return false;
        }
        Task taskAfter = allTasks.ceiling(newTask);
        if (taskAfter != null && !newTask.getEndTime().isBefore(taskAfter.getStartTime())) {
            return false;
        }
        return true;
    }
}