package ac.za.mycput.taskmanager.Service;

import ac.za.mycput.taskmanager.Domain.Task;
import ac.za.mycput.taskmanager.Domain.User;
import ac.za.mycput.taskmanager.Exception.ResourceNotFoundException;
import ac.za.mycput.taskmanager.Repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    private TaskService taskService;

    @BeforeEach
    void setUp() {
        taskService = new TaskService(taskRepository);
    }

    @Test
    void create() {
        Task incoming = new Task.Builder()
                .setTitle("New Task")
                .setCompleted(false)
                .setDueDate(LocalDate.now().plusDays(1))
                .build();

        Mockito.when(taskRepository.save(Mockito.any(Task.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Task result = taskService.create(incoming);

        assertEquals("New Task", result.getTitle());
        assertFalse(result.isCompleted());
    }

    @Test
    void read() {
        Task task = new Task.Builder()
                .setId(1L)
                .setTitle("Existing Task")
                .build();

        Mockito.when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        Task result = taskService.read(1L);

        assertEquals("Existing Task", result.getTitle());
    }

    @Test
    void read_whenMissing() {
        Mockito.when(taskRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            taskService.read(99L);
        });
    }

    @Test
    void update() {
        Task existing = new Task.Builder()
                .setId(1L)
                .setTitle("Old Title")
                .setCompleted(false)
                .setDueDate(LocalDate.now().plusDays(1))
                .build();

        Mockito.when(taskRepository.findById(1L)).thenReturn(Optional.of(existing));
        Mockito.when(taskRepository.save(Mockito.any(Task.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Task updateRequest = new Task.Builder()
                .setId(1L)
                .setTitle("New Title")
                .setCompleted(true)
                .setDueDate(LocalDate.now().plusDays(5))
                .build();

        Task result = taskService.update(updateRequest);

        assertEquals("New Title", result.getTitle());
        assertTrue(result.isCompleted());
    }

    @Test
    void update_whenTaskDoesNotExist() {
        Mockito.when(taskRepository.findById(99L)).thenReturn(Optional.empty());

        Task updateRequest = new Task.Builder()
                .setId(99L)
                .setTitle("Doesn't Matter")
                .build();

        Task result = taskService.update(updateRequest);

        assertNull(result);
    }

    @Test
    void delete() {
        Mockito.when(taskRepository.existsById(1L)).thenReturn(true);

        boolean result = taskService.delete(1L);

        assertTrue(result);
        Mockito.verify(taskRepository).deleteById(1L);
    }

    @Test
    void delete_whenTaskDoesNotExist() {
        Mockito.when(taskRepository.existsById(99L)).thenReturn(false);

        boolean result = taskService.delete(99L);

        assertFalse(result);
    }

    @Test
    void findByTitle() {
        Task task = new Task.Builder().setId(1L).setTitle("Shared Title").build();

        Mockito.when(taskRepository.findByTitle("Shared Title"))
                .thenReturn(List.of(task));

        List<Task> result = taskService.findByTitle("Shared Title");

        assertEquals(1, result.size());
        assertEquals("Shared Title", result.get(0).getTitle());
    }

    @Test
    void findByUser() {
        User user = new User.Builder().setId(1L).setName("Aidan").setEmail("aidan@example.com").build();
        Task task = new Task.Builder().setId(1L).setTitle("Assigned Task").setUser(user).build();

        Mockito.when(taskRepository.findByUser(user)).thenReturn(List.of(task));

        List<Task> result = taskService.findByUser(user);

        assertEquals(1, result.size());
        assertEquals("Assigned Task", result.get(0).getTitle());
    }

    @Test
    void findByUserId_returnsTasksForThatUserId() {
        Task task = new Task.Builder().setId(1L).setTitle("Assigned Task").build();

        Mockito.when(taskRepository.findByUserId(1L)).thenReturn(List.of(task));

        List<Task> result = taskService.findByUserId(1L);

        assertEquals(1, result.size());
        assertEquals("Assigned Task", result.get(0).getTitle());
    }

    @Test
    void getAll() {
        Task task1 = new Task.Builder().setId(1L).setTitle("Task One").build();
        Task task2 = new Task.Builder().setId(2L).setTitle("Task Two").build();

        Mockito.when(taskRepository.findAll()).thenReturn(List.of(task1, task2));

        List<Task> result = taskService.getAll();

        assertEquals(2, result.size());
    }
}