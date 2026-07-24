package ac.za.mycput.taskmanager.Controller;


import ac.za.mycput.taskmanager.Domain.User;
import ac.za.mycput.taskmanager.Service.impl.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private IUserService userService;

    @Autowired
    UserController(UserController userController){
        this.userService = userService;
    }

    @PostMapping("/create")
    public User create(@RequestBody User user){
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
        userService.delete(id);
        return false;
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
    public List<User> getAll(){
        return userService.getAll();
    }
}
