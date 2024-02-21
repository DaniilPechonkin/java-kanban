package tasks;

import manager.HistoryManager;
import manager.InMemoryTaskManager;
import manager.Manager;
import manager.TaskManager;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {
    InMemoryTaskManager taskManager = new InMemoryTaskManager();

    @Test
    void addNewEpic() {
        Epic epic = new Epic("epic", "epic");
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