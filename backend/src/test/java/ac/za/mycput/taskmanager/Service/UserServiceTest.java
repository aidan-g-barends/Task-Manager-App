package ac.za.mycput.taskmanager.Service;

import ac.za.mycput.taskmanager.Domain.User;
import ac.za.mycput.taskmanager.Exception.ResourceNotFoundException;
import ac.za.mycput.taskmanager.Repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository);
    }

    @Test
    void create() {
        User incoming = new User.Builder()
                .setName("Aidan")
                .setEmail("aidan@example.com")
                .build();

        Mockito.when(userRepository.save(Mockito.any(User.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        User result = userService.create(incoming);

        assertEquals("Aidan", result.getName());
        assertEquals("aidan@example.com", result.getEmail());
    }

    @Test
    void read() {
        User user = new User.Builder()
                .setId(1L)
                .setName("Existing User")
                .setEmail("existing@example.com")
                .build();

        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User result = userService.read(1L);

        assertEquals("Existing User", result.getName());
    }

    @Test
    void read_whenMissing() {
        Mockito.when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            userService.read(99L);
        });
    }

    @Test
    void update() {
        User existing = new User.Builder()
                .setId(1L)
                .setName("Old Name")
                .setEmail("old@example.com")
                .build();

        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(existing));
        Mockito.when(userRepository.save(Mockito.any(User.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        User updateRequest = new User.Builder()
                .setId(1L)
                .setName("New Name")
                .setEmail("new@example.com")
                .build();

        User result = userService.update(updateRequest);

        assertEquals("New Name", result.getName());
        assertEquals("new@example.com", result.getEmail());
    }

    @Test
    void update_whenUserDoesNotExist() {
        Mockito.when(userRepository.findById(99L)).thenReturn(Optional.empty());

        User updateRequest = new User.Builder()
                .setId(99L)
                .setName("Doesn't Matter")
                .build();

        User result = userService.update(updateRequest);

        assertNull(result);
    }

    @Test
    void delete_whenUserExists() {
        Mockito.when(userRepository.existsById(1L)).thenReturn(true);

        boolean result = userService.delete(1L);

        assertTrue(result);
        Mockito.verify(userRepository).deleteById(1L);
    }

    @Test
    void delete_whenUserDoesNotExist() {
        Mockito.when(userRepository.existsById(99L)).thenReturn(false);

        boolean result = userService.delete(99L);

        assertFalse(result);
    }

    @Test
    void findByEmail() {
        User user = new User.Builder().setId(1L).setName("Aidan").setEmail("aidan@example.com").build();

        Mockito.when(userRepository.findByEmail("aidan@example.com")).thenReturn(user);

        User result = userService.findByEmail("aidan@example.com");

        assertEquals("Aidan", result.getName());
    }

    @Test
    void findByName() {
        User user = new User.Builder().setId(1L).setName("Shared Name").setEmail("shared@example.com").build();

        Mockito.when(userRepository.findByName("Shared Name")).thenReturn(List.of(user));

        List<User> result = userService.findByName("Shared Name");

        assertEquals(1, result.size());
        assertEquals("Shared Name", result.get(0).getName());
    }

    @Test
    void getAll() {
        User user1 = new User.Builder().setId(1L).setName("User One").setEmail("one@example.com").build();
        User user2 = new User.Builder().setId(2L).setName("User Two").setEmail("two@example.com").build();

        Mockito.when(userRepository.findAll()).thenReturn(List.of(user1, user2));

        List<User> result = userService.getAll();

        assertEquals(2, result.size());
    }
}