package ac.za.mycput.taskmanager.Repository;

import ac.za.mycput.taskmanager.Domain.Task;
import ac.za.mycput.taskmanager.Domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository <Task, Long> {

    Task findByTitle(String title);

    List<Task> findByUser(User user);
}
