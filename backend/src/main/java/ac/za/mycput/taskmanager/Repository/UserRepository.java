package ac.za.mycput.taskmanager.Repository;

import ac.za.mycput.taskmanager.Domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository <User, Long > {

    User findByEmail(String email);

    List<User> findByName(String name);
}
