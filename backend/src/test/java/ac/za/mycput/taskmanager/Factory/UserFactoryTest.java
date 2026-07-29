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
       User user = UserFactory.createUser("Aidan", "aidan@gmail.com", null);

       assertEquals("Aidan", user.getName());
       assertEquals("aidan@gmail.com", user.getEmail());
   }

   @Test
   void createUser_whenNameIsNullOrEmpty(){
       assertThrows(IllegalArgumentException.class, () -> {
           UserFactory.createUser("", "aidan@gmail.com", null);
       });
   }

    @Test
    void createUser_throwsException_whenEmailIsInvalid() {
        assertThrows(IllegalArgumentException.class, () -> {
            UserFactory.createUser("Aidan", "notanemail", null);
        });
    }
}