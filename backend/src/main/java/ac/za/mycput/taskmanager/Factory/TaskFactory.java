package ac.za.mycput.taskmanager.Factory;

import ac.za.mycput.taskmanager.Domain.Task;
import ac.za.mycput.taskmanager.Domain.User;
import ac.za.mycput.taskmanager.util.Helper;

import java.time.LocalDate;

public class TaskFactory {

    public static Task createTask(Long id, String title, boolean completed, LocalDate dueDate, User user){
        if(Helper.isNullOrEmpty(title) || !Helper.isValidDate(dueDate)){

            return null;
        }

        return new Task.Builder()
                .setId(id)
                .setTitle(title)
                .setCompleted(completed)
                .setDueDate(dueDate)
                .setUser(user)
                .build();

    }


}
