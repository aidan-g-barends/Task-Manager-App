package ac.za.mycput.taskmanager.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

class HelperTest {

    @Test
    void isValidEmail_returnsTrue_forValidEmail() {
        assertTrue(Helper.isValidEmail("user@example.com"));
    }

    @Test
    void isValidEmail_returnsFalse_forMissingAtSymbol() {
        assertFalse(Helper.isValidEmail("notanemail"));
    }
}