package ac.za.mycput.taskmanager.Factory;

import ac.za.mycput.taskmanager.Domain.User;
import ac.za.mycput.taskmanager.util.Helper;



public class UserFactory {

    public static User createUser(String name, String email, Long id) {
        if (Helper.isNullOrEmpty(name) || !Helper.isValidEmail(email)) {
            return null;
        }

        return new User.Builder()
                .setName(name)
                .setEmail(email)
                .setId(id)
                .build();

    }
}
