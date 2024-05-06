package tasks;

import manager.InMemoryTaskManager;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {
    InMemoryTaskManager taskManager = new InMemoryTaskManager();

    @Test
    void addNewEpic() {
        Epic epic = new Epic("epic", "epic");
        epic.setStartTime(epic.getStartTime().plusMinutes(50));
        epic.setDuration(Duration.ofMinutes(3));
        taskManager.addEpic(epic);
        Epic savedEpic = taskManager.findEpic(epic.getId());

        assertNotNull(savedEpic, "Задача не найдена.");
        assertEquals(epic, savedEpic, "Задачи не совпадают.");

        final ArrayList<Epic> epics = taskManager.getAllEpics();

        assertNotNull(epics, "Задачи не возвращаются.");
        assertEquals(1, epics.size(), "Неверное количество задач.");
        assertEquals(epic, epics.get(0), "Задачи не совпадают.");
    }

    @Test
    public void canFindId() {
        Epic epic1 = new tasks.Epic("firstEpic", "123");
        assertEquals(taskManager.findSubtask(1).getId(), epic1.getId());
    }

    @Test
    public void isCopyOfEpicEqualsWithSameId() {
        Epic epic1 = new Epic("first", "first");
        Epic epic2 = new Epic("second", "second");
        Task epic3 = epic1;
        assertEquals(epic1, epic3);
        assertNotEquals(epic1, epic2);
    }
}