package ac.za.mycput.taskmanager.util;

import java.time.LocalDate;

public class Helper {

    public static boolean isNullOrEmpty(String string) {
        return string == null || string.isEmpty();
    }

    public static boolean isValidEmail(String email) {
        return email != null && email.contains("@") && email.contains(".");
    }

    public static boolean isValidDate(LocalDate localDate){
        return localDate != null && !localDate.isBefore(LocalDate.now());
    }

}