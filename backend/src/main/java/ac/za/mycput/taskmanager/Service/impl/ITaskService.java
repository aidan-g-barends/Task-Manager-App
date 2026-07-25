package ac.za.mycput.taskmanager.Service.impl;

import ac.za.mycput.taskmanager.Domain.Task;
import ac.za.mycput.taskmanager.Domain.User;

import java.util.List;

public interface ITaskService extends IService<Task, Long> {

    List<Task> findByTitle(String title);

    List<Task> findByUser(User user);

    List<Task> getAll();

}