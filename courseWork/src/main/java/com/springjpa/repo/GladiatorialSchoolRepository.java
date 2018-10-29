package com.springjpa.repo;

import com.springjpa.models.GladiatorialSchool;
import org.springframework.data.repository.CrudRepository;

public interface GladiatorialSchoolRepository extends CrudRepository<GladiatorialSchool,Integer> {
    GladiatorialSchool findByName(String name);
}
