package ac.za.mycput.taskmanager.Service;

import ac.za.mycput.taskmanager.Domain.User;
import ac.za.mycput.taskmanager.Repository.UserRepository;
import ac.za.mycput.taskmanager.Service.impl.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public User create(User user) {

        return userRepository.save(user);
    }

    @Override
    public User read(Long id) {

        return userRepository.findById(id).get();
    }

    @Override
    public User update(User user) {
        User existing = userRepository.findById(user.getId()).orElse(null);
        if(existing == null){
            return null;
        }
        User updated = new User.Builder()
                .copy(existing)
                .setName(user.getName())
                .setEmail(user.getEmail())
                .build();
        return userRepository.save(updated);
    }

    @Override
    public boolean delete(Long id) {

        if(!userRepository.existsById(id)){
            return false;
        }
        userRepository.deleteById(id);
        return true;
    }
    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> findByName(String name) {
        return userRepository.findByName(name);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }
}
