package ac.za.mycput.taskmanager.Factory;

import ac.za.mycput.taskmanager.Domain.User;
import ac.za.mycput.taskmanager.util.Helper;



public class UserFactory {

    public static User createUser(String name, String email, Long id) {
        if (Helper.isNullOrEmpty(name)){
            throw new IllegalArgumentException("Name cannot be null or empty");

        } if(!Helper.isValidEmail(email)) {
            throw new IllegalArgumentException("Email must be a valid email address contain 'a' and '.com' E.g user@email.com");
        }

        return new User.Builder()
                .setName(name)
                .setEmail(email)
                .setId(id)
                .build();

    }
}
