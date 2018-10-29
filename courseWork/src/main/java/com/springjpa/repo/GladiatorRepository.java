package com.springjpa.repo;

import com.springjpa.models.Gladiator;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GladiatorRepository extends CrudRepository<Gladiator,Integer> {
    Gladiator findByName(String name);
    List<Gladiator> findByMasterIsNull();
    List<Gladiator> getAllByNameNotNullOrderByWins();
}
