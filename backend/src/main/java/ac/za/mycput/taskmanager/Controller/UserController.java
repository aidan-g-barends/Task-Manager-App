package ac.za.mycput.taskmanager.Controller;


import ac.za.mycput.taskmanager.Domain.User;
import ac.za.mycput.taskmanager.Service.impl.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final IUserService userService;

    @Autowired
    UserController(IUserService userService){
        this.userService = userService;
    }

    @PostMapping("/create")
    public User create(@Valid @RequestBody User user){
        return userService.create(user);
    }

    @GetMapping("/read/{id}")
    public User read(@PathVariable Long id){
        return userService.read(id);
    }

    @PutMapping("/update")
    public User update(@RequestBody User user){
        return userService.update(user);
    }

    @DeleteMapping("/delete/{id}")
    public boolean delete(@PathVariable Long id){
        return userService.delete(id);
    }

    @GetMapping("/email/{email}")
    public User findByEmail(@PathVariable String email) {
        return userService.findByEmail(email);
    }

    @GetMapping("/names/{name}")
    public List<User> findByName(@PathVariable String name){
        return userService.findByName(name);
    }


    @GetMapping("/getAll")
    public Page<User> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return userService.getAll(pageable);
    }
}