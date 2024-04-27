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
    protected final TreeSet<Task> sortedByTimeTasks;
    private int count = 0;

    public InMemoryTaskManager() {
        Comparator<Task> compareByStartTime = Comparator.comparing((Task::getStartTime));
        this.sortedByTimeTasks = new TreeSet<>(compareByStartTime);
    }

    //методы создания
    @Override
    public void addTask(Task newTask) {
        int id = addId();
        newTask.setId(id);
        tasks.put(id, newTask);
        if (isTasksCross(newTask)) {
            sortedByTimeTasks.add(newTask);
        }
    }

    @Override
    public void addSubtask(Subtask newSubtask) {
        Epic epic = epics.get(newSubtask.getEpicId());
        int subtaskId = addId();
        newSubtask.setId(subtaskId);
        subtasks.put(subtaskId, newSubtask);
        if (isTasksCross(newSubtask)) {
            epic.getSubtasksId().add(subtaskId);
            changeStatus(epic.getId());
            calculateTime(epic.getId());
            sortedByTimeTasks.add(newSubtask);
        }
    }

    @Override
    public void addEpic(Epic newEpic) {
        int id = addId();
        newEpic.setId(id);
        epics.put(id, newEpic);

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
        sortedByTimeTasks.remove(tasks.get(id));
        tasks.remove(id);
        historyManager.remove(id);
    }

    @Override
    public void removeEpic(Integer id) {
        Epic epic = epics.get(id);
        epic.getSubtasksId().stream()
                        .forEach(subtaskId -> {
                            sortedByTimeTasks.remove(subtasks.get(subtaskId));
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
        sortedByTimeTasks.remove(subtasks.get(id));
        subtasks.remove(id);
        historyManager.remove(id);
        changeStatus(epicId);
        calculateTime(epicId);
    }

    //методы удаления всех задач
    @Override
    public void removeAllTasks() {
        tasks.keySet().stream()
                .forEach(id-> {
                    sortedByTimeTasks.remove(tasks.get(id));
                    tasks.remove(id);
                    historyManager.remove(id);
                });
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
                            calculateTime(epic.getId());
                        });
    }

    //методы обновления задач
    @Override
    public void updateTask(Task newTask) {
        if (isTasksCross(newTask)) {
            sortedByTimeTasks.remove(subtasks.get(newTask.getId()));
            sortedByTimeTasks.add(newTask);
            tasks.put(newTask.getId(), newTask);
        }
    }

    @Override
    public void updateSubtask(Subtask newSubtask) {
        Integer epicId = newSubtask.getEpicId();
        if (isTasksCross(newSubtask)) {
            sortedByTimeTasks.remove(subtasks.get(newSubtask.getId()));
            sortedByTimeTasks.add(newSubtask);
            subtasks.put(epicId, newSubtask);
        }
        changeStatus(epicId);
        calculateTime(epicId);
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

    public HistoryManager getHistoryManager() {
        return historyManager;
    }

    public Set<Task> getPrioritizedTasks() {
        return sortedByTimeTasks;
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

    private Duration getEpicDuration(Integer epicId) {
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

    private LocalDateTime getEpicEndTime(Integer epicId) {
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

    private LocalDateTime getEpicStartTime(Integer epicId) {
        LocalDateTime curStartTime = LocalDateTime.now();
        for (Subtask subtask : subtasks.values()) {
            if (subtask.getEpicId() == epicId) {
                if (subtask.getEndTime().isBefore(curStartTime)) {
                    curStartTime = subtask.getEndTime();
                }
            }
        }
        return curStartTime;
    }

    private boolean isTasksCross(Task newTask) {
        Task taskBefore = sortedByTimeTasks.floor(newTask);
        if (taskBefore != null && !taskBefore.getEndTime().isBefore(newTask.getStartTime())) {
            return false;
        }
        Task taskAfter = sortedByTimeTasks.ceiling(newTask);
        if (taskAfter != null && !newTask.getEndTime().isBefore(taskAfter.getStartTime())) {
            return false;
        }
        return true;
    }

    private void calculateTime(Integer epicId) {
        Epic epic = epics.get(epicId);
        epic.setStartTime(getEpicStartTime(epic.getId()));
        epic.setDuration(getEpicDuration(epic.getId()));
        epic.setEndTime(getEpicEndTime(epic.getId()));
    }
}