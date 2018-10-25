package com.springjpa.repo;

import com.springjpa.model.Point;
import org.springframework.data.repository.CrudRepository;

public interface PointRepository extends CrudRepository<Point,Integer> {
}
