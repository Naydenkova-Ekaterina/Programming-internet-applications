package com.springjpa.repo;

import com.springjpa.models.Armor;
import org.springframework.data.repository.CrudRepository;

public interface ArmorRepository extends CrudRepository<Armor,Integer> {
    public Armor findByName(String name);
}
