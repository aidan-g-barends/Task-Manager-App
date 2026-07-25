package ac.za.mycput.taskmanager.Controller;

import ac.za.mycput.taskmanager.Domain.Task;
import ac.za.mycput.taskmanager.Service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService){
        this.taskService = taskService;
    }

    @PostMapping("/create")
    public Task create(@RequestBody Task task){
        return taskService.create(task);
    }

    @GetMapping("/read/{id}")
    public Task read(@PathVariable Long id){
        return taskService.read(id);
    }

    @PutMapping("/update")
    public Task update(@RequestBody Task task){
        return taskService.update(task);
    }

    @DeleteMapping("/delete/{id}")
    public boolean delete(@PathVariable Long id){
        return taskService.delete(id);
    }

    @GetMapping("/getAll")
    public List<Task> getAll(){
        return taskService.getAll();
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