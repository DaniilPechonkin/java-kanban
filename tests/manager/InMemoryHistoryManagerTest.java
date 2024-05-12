package manager;

import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.Task;
import manager.HistoryManager;
import manager.Manager;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {
    HistoryManager historyManager = Manager.getDefaultHistory();

    @Test
    public void canGetHistory() {
        //Пустая история
        ArrayList<Task> history = new ArrayList<>();
        assertEquals(historyManager.getHistory(), history);

        Task task1 = new tasks.Task("firstTask", "1");
        Task task2 = new tasks.Task("secondTask", "2");
        Epic epic1 = new tasks.Epic("firstEpic", "123");
        historyManager.addInHistory(epic1);
        historyManager.addInHistory(task1);
        historyManager.addInHistory(task2);
        history.add(epic1);
        history.add(task1);
        history.add(task2);

        //Добавление
        assertNotNull(historyManager.getHistory());
        assertEquals(historyManager.getHistory(), history);
    }
}