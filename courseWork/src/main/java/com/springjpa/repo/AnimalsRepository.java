package com.springjpa.repo;

import com.springjpa.models.Animal;
import org.springframework.data.repository.CrudRepository;

public interface AnimalsRepository extends CrudRepository<Animal,Integer> {
    public Animal findByName(String name);

}
