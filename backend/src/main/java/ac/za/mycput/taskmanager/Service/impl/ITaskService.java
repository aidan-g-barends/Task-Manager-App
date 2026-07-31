package ac.za.mycput.taskmanager.Service.impl;

import ac.za.mycput.taskmanager.Domain.Task;
import ac.za.mycput.taskmanager.Domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ITaskService extends IService<Task, Long> {

    List<Task> findByTitle(String title);

    List<Task> findByUser(User user);

    List <Task> findByUserId(Long userId);

    List<Task> getAll();

    Page<Task> getAll(Pageable pageable);

}