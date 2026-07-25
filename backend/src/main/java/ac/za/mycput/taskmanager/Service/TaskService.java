package ac.za.mycput.taskmanager.Service;

import ac.za.mycput.taskmanager.Domain.Task;
import ac.za.mycput.taskmanager.Domain.User;
import ac.za.mycput.taskmanager.Repository.TaskRepository;
import ac.za.mycput.taskmanager.Service.impl.ITaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService implements ITaskService {

    private TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }


    @Override
    public Task create(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public Task read(Long id) {
        return taskRepository.findById(id).get();
    }

    @Override
    public Task update(Task task) {
        Task existing = this.taskRepository.findById(task.getId()).orElse(null);
        if (existing == null) {
            return null;
        }
        Task updated = new Task.Builder()
                .copy(existing)
                .setDueDate(task.getDueDate())
                .setTitle(task.getTitle())
                .build();
        return this.taskRepository.save(updated);
    }

    @Override
    public boolean delete(Long id) {
        if(!taskRepository.existsById(id)){
            return false;
        }taskRepository.deleteById(id);
        return true;
    }

    @Override
    public Task findByTitle(String title)
    {
        return taskRepository.findByTitle(title);
    }

    @Override
    public List<Task> findByUser(User user)
    {
        return taskRepository.findByUser(user);
    }

    @Override
    public List<Task> getAll() {
        return taskRepository.findAll();
    }
}

