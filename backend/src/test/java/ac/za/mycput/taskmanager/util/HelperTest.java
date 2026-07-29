package ac.za.mycput.taskmanager.util;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

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

    @Test
    void isNullOrEmpty_returnsTrue_forNull() {
        assertTrue(Helper.isNullOrEmpty(null));
    }

    @Test
    void isNullOrEmpty_returnsTrue_forEmptyString() {
        assertTrue(Helper.isNullOrEmpty(""));
    }

    @Test
    void isNullOrEmpty_returnsFalse_forNormalString() {
        assertFalse(Helper.isNullOrEmpty("hello"));
    }

    @Test
    void isValidDate_returnTrue_forValidDate(){
        assertTrue(Helper.isValidDate(LocalDate.now()));
    }

    @Test
    void isValidDate_returnFalse_forInvalidDate(){
        assertFalse(Helper.isValidDate(null));
    }

    @Test
    void isValidDate_returnsFalse_forPastDate() {
        assertFalse(Helper.isValidDate(LocalDate.now().minusDays(1)));
    }

    @Test
    void isValidDate_returnsTrue_forFutureDate() {
        assertTrue(Helper.isValidDate(LocalDate.now().plusDays(1)));
    }
}