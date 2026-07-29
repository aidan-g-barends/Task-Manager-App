package ac.za.mycput.taskmanager.Factory;

import ac.za.mycput.taskmanager.Domain.Task;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TaskFactoryTest {

    @Test
    void createTaskWithValidInput() {
        Task task = TaskFactory.createTask(null, "Take the dogs for a walk", false, LocalDate.now().plusDays(1), null);

        assertEquals("Take the dogs for a walk", task.getTitle());
        assertFalse(task.isCompleted());
    }

    @Test
    void createTaskWhenTitleIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> {
            TaskFactory.createTask(null, "", false, LocalDate.now().plusDays(1), null);
        });
    }

    @Test
    void createTaskWhenTitleIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            TaskFactory.createTask(null, null, false, LocalDate.now().plusDays(1), null);
        });
    }

    @Test
    void createTaskWhenDueDateIsInThePast() {
        assertThrows(IllegalArgumentException.class, () -> {
            TaskFactory.createTask(null, "Valid Title", false, LocalDate.now().minusDays(1), null);
        });
    }
}