package com.springjpa.repo;

import com.springjpa.models.Master;
import com.springjpa.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User,Integer> {
    User findByUsername(String username);
    User findByMaster(Master master);
    List<User> findByMasterIsNotNullOrderByMoney();
    List<User> findUsersByMasterIsNotNullOrderByMoney();
}
