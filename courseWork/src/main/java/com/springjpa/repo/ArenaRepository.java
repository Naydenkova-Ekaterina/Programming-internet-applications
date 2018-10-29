package com.springjpa.repo;

import com.springjpa.models.Arena;
import org.springframework.data.repository.CrudRepository;

public interface ArenaRepository extends CrudRepository<Arena,Integer> {
    public Arena findByName(String name);
}
