package ac.za.mycput.taskmanager.Controller;

import ac.za.mycput.taskmanager.Domain.Task;
import ac.za.mycput.taskmanager.Service.TaskService;
import ac.za.mycput.taskmanager.Service.impl.ITaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task")
public class TaskController {

    private final ITaskService taskService;

    @Autowired
    public TaskController(ITaskService taskService){
        this.taskService = taskService;
    }

    @PostMapping("/create")
    public Task create(@Valid @RequestBody Task task){
        return taskService.create(task);
    }

    @GetMapping("/read/{id}")
    public Task read(@PathVariable Long id){
        return taskService.read(id);
    }

    @PutMapping("/update")
    public Task update(@Valid @RequestBody Task task){
        return taskService.update(task);
    }

    @DeleteMapping("/delete/{id}")
    public boolean delete(@PathVariable Long id){
        return taskService.delete(id);
    }

    @GetMapping("/getAll")
    public Page<Task> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return taskService.getAll(pageable);
    }

    @GetMapping("/title/{title}")
    public List<Task> findByTitle(@PathVariable String title){
        return taskService.findByTitle(title);
    }

    @GetMapping("/user/{userId}")
    public List<Task> findByUser(@PathVariable Long userId){
        return taskService.findByUserId(userId);
    }
}