package com.springjpa.repo;

import com.springjpa.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Integer> {
   // public Boolean findByUsernameAndPassword(User user);
    public User findByUsername(String username);
}
