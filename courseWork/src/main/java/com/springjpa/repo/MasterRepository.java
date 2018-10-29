package com.springjpa.repo;

import com.springjpa.models.Master;
import org.springframework.data.repository.CrudRepository;

public interface MasterRepository extends CrudRepository<Master,Integer> {
    public Master findByName(String name);
}
