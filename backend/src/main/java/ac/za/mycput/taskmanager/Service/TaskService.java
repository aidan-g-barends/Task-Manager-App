package ac.za.mycput.taskmanager.Service;

import ac.za.mycput.taskmanager.Domain.Task;
import ac.za.mycput.taskmanager.Domain.User;
import ac.za.mycput.taskmanager.Exception.ResourceNotFoundException;
import ac.za.mycput.taskmanager.Factory.TaskFactory;
import ac.za.mycput.taskmanager.Repository.TaskRepository;
import ac.za.mycput.taskmanager.Service.impl.ITaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

        Task newTask = TaskFactory.createTask(null, task.getTitle(), task.isCompleted(),  task.getDueDate() , task.getUser());
        return taskRepository.save(newTask);
    }

    @Override
    public Task read(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task with id: " + id + " not found"));
    }

    @Override
    public Task update(Task task) {
        Task existing = this.taskRepository.findById(task.getId()).orElse(null);
        if (existing == null) {
            return null;
        }
        Task updated = new Task.Builder()
                .copy(existing)
                .setTitle(task.getTitle())
                .setCompleted(task.isCompleted())
                .setDueDate(task.getDueDate())
                .setUser(task.getUser())
                .build();
        return this.taskRepository.save(updated);
    }

    @Override
    public boolean delete(Long id) {
        if (!taskRepository.existsById(id)) {
            return false;
        }
        taskRepository.deleteById(id);
        return true;
    }

    @Override
    public List<Task> findByTitle(String title) {
        return taskRepository.findByTitle(title);
    }

    @Override
    public List<Task> findByUser(User user) {
        return taskRepository.findByUser(user);
    }

    @Override
    public List<Task> findByUserId(Long userId) {
        return taskRepository.findByUserId(userId);
    }

    @Override
    public List<Task> getAll() {
        return taskRepository.findAll();
    }

    @Override
    public Page<Task> getAll(Pageable pageable) {
        return taskRepository.findAll(pageable);
    }
}