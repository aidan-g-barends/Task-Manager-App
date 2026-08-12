package ac.za.mycput.taskmanager.Factory;

import ac.za.mycput.taskmanager.Domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.MethodName.class)
class UserFactoryTest {

    @Test
    void createValidUser(){
        User user = UserFactory.createUser("Aidan", "aidan@gmail.com", "TestPass123!", null);

        assertEquals("Aidan", user.getName());
        assertEquals("aidan@gmail.com", user.getEmail());
    }

    @Test
    void createUser_whenNameIsNullOrEmpty(){
        assertThrows(IllegalArgumentException.class, () -> {
            UserFactory.createUser("", "aidan@gmail.com", "TestPass123!", null);
        });
    }

    @Test
    void createUser_throwsException_whenEmailIsInvalid() {
        assertThrows(IllegalArgumentException.class, () -> {
            UserFactory.createUser("Aidan", "notanemail", "TestPass123!", null);
        });
    }

    @Test
    void createUser_throwsException_whenPasswordIsBlank() {
        assertThrows(IllegalArgumentException.class, () -> {
            UserFactory.createUser("Aidan", "aidan@gmail.com", "", null);
        });
    }
}