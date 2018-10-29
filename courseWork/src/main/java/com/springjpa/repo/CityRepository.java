package com.springjpa.repo;
import com.springjpa.models.City;
import org.springframework.data.repository.CrudRepository;


public interface CityRepository extends CrudRepository<City,Integer> {

    City findByName(String name);

}
