package ac.za.mycput.taskmanager.util;

import java.time.LocalDate;

public class Helper {

    public static boolean isNullOrEmpty(String string) {
        if( string == null || string.isEmpty()){
            return true;
        }
        return false;
    }

    public static boolean isValidEmail(String email) {
        return (email == null || !email.contains("@") || !email.contains("."));

    }

    public static boolean isValidDate(LocalDate localDate){
        if(localDate != null && !localDate.isBefore(LocalDate.now())){
            return true;
        }
        return false;
    }

}
