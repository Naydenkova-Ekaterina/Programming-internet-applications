package com.springjpa.controller;

import com.springjpa.model.Point;
import com.springjpa.repo.PointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/points")
public class PointController {

    @Autowired
    PointRepository repository;

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Point> getAllJobs() {
        return repository.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Point findOne(@PathVariable("id") Integer id) {
        return repository.findOne(id);
    }

    @RequestMapping(method = RequestMethod.POST, consumes= MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Point create(@RequestBody Point resource) {
        resource.check();
        //return resource;
        return repository.save(resource);
    }

}
