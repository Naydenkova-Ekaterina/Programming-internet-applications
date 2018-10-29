package com.springjpa.controller;

import com.springjpa.models.GladiatorialSchool;
import com.springjpa.models.Job;
import com.springjpa.repo.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/jobs")
public class JobController {

    @Autowired
    JobRepository repository;

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Job> getAllJobs() {
        return repository.findAll();
    }

    @RequestMapping(value = "findOne/{id}", method = RequestMethod.GET)
    public Job findOne(@PathVariable("id") Integer id) {
        return repository.findOne(id);
    }


    @RequestMapping(value = "save",method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Job create(@RequestBody Job resource) {
         return repository.save(resource);
    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") Integer id) {
        repository.delete(id);
    }

    @RequestMapping(value = "update/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable( "id" ) Integer id, @RequestBody Job resource) {

        resource.setId(id);
        repository.save(resource);
    }

    @RequestMapping(value = "getCrudJobs", method = RequestMethod.GET)
    public Map<String,Object> getCrudJobs() {
        Map<String, Object> map = new HashMap<>();
        int index = 0;
        for (Job job:repository.findAll()) {
            map.put("name"+index,job.getName());
            map.put("reward"+index,job.getReward());
            index++;
        }

        return map;
    }

    @RequestMapping(value = "findJobByName/{name}", method = RequestMethod.GET)
    public Job findJobByName(@PathVariable("name") String name){
        return repository.findByName(name);
    }

    @RequestMapping(value = "findJobNames", method = RequestMethod.GET)
    public ArrayList<String> findJobNames(){
        ArrayList<String>list=new ArrayList<>();
        for (Job job:repository.findAll()) {
            list.add(job.getName());
        }
        return list;
    }


}
