package com.springjpa.repo;

import com.springjpa.model.UserRole;
import org.springframework.data.repository.CrudRepository;

public interface UserRoleRepository extends CrudRepository<UserRole,Integer> {
}
