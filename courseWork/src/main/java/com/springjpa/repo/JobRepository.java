package com.springjpa.repo;

import com.springjpa.models.Job;
import org.springframework.data.repository.CrudRepository;

public interface JobRepository extends CrudRepository<Job,Integer> {
    Job findByName(String name);
}
