package com.springjpa.controller;

import com.springjpa.models.City;
import com.springjpa.repo.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cities")
public class CityController {
	@Autowired
	CityRepository repository;

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<City> getAll() {
        return repository.findAll();
    }

    @RequestMapping(value = "findOne/id/{id}", method = RequestMethod.GET)
    public City findOne(@PathVariable("id") Integer id) {
        return repository.findOne(id);
    }


    @RequestMapping(value = "save",method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public City create(@RequestBody City resource) {
        return repository.save(resource);
    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") Integer id) {
        repository.delete(id);
    }

    @RequestMapping(value = "update/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable( "id" ) Integer id, @RequestBody City resource) {
        resource.setId(id);
        repository.save(resource);
    }

    @RequestMapping(value = "name/{name}", method = RequestMethod.GET)
    public City fetchDataByName(@PathVariable("name") String name){
        City city=repository.findByName(name);
        return city;
    }

    @RequestMapping(value = "getCrudCities", method = RequestMethod.GET)
    public Map<String,Object> getCrudCities() {
        Map<String, Object> map = new HashMap<>();
        int index = 0;
        for (City city:repository.findAll()) {
            map.put("name"+index,city.getName());
            map.put("numberOfResidents"+index,city.getNumberOfResidents());
            index++;
        }

        return map;
    }

    @RequestMapping(value = "getCityNames", method = RequestMethod.GET)
    public ArrayList<String> getCityNames() {
        ArrayList<String> list = new ArrayList<>();
        for (City city:repository.findAll()) {
           list.add(city.getName());
        }

        return list;
    }

}

