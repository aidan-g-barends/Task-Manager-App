package ac.za.mycput.taskmanager.Service.impl;

import ac.za.mycput.taskmanager.Domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IUserService extends IService<User, Long> {

    User findByEmail(String email);

    List<User> findByName(String name);

    List<User> getAll();

    Page<User> getAll(Pageable pageable);
}

