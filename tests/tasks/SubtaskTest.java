package tasks;

import manager.InMemoryTaskManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class SubtaskTest {
    InMemoryTaskManager taskManager = new InMemoryTaskManager();
    Epic epic;
    Subtask subtask;

    @BeforeEach
    public void beforeEach() {
        epic = new Epic("1", "2");
        epic.setStartTime(epic.getStartTime().plusMinutes(50));
        epic.setDuration(Duration.ofMinutes(3));
        taskManager.addEpic(epic);
        subtask = new Subtask("epic", "epic",1);
        subtask.setStartTime(epic.getStartTime().plusMinutes(50));
        subtask.setDuration(Duration.ofMinutes(3));
        taskManager.addSubtask(subtask);
    }

    @Test
    void addNewSubtask() {
        Subtask savedSubtask = taskManager.findSubtask(subtask.getId());

        assertNotNull(savedSubtask, "Задача не найдена.");
        assertEquals(subtask, savedSubtask, "Задачи не совпадают.");

        final ArrayList<Subtask> subtasks = taskManager.getAllSubtasks();

        assertNotNull(subtasks, "Задачи не возвращаются.");
        assertEquals(1, subtasks.size(), "Неверное количество задач.");
        assertEquals(subtask, subtasks.get(0), "Задачи не совпадают.");
    }

    @Test
    public void canFindId() {
        assertEquals(taskManager.findSubtask(2).getId(), subtask.getId());
    }

    @Test
    public void isCopyOfSubtaskEqualsWithSameId() {
        Epic epic1 = new tasks.Epic("firstEpic", "123");
        Subtask subtask1 = new tasks.Subtask("first", "first",1);
        Subtask subtask2 = new tasks.Subtask("second", "second",1);
        Subtask subtask3 = subtask1;
        assertEquals(subtask1, subtask3);
        assertNotEquals(subtask1, subtask2);
    }

}