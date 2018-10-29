package com.springjpa.repo;

import com.springjpa.models.Fight;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FightRepository extends CrudRepository<Fight,Integer> {

}
