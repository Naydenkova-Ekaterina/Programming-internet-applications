package com.springjpa.controller;

import com.springjpa.models.City;
import com.springjpa.models.GladiatorialSchool;
import com.springjpa.models.Master;
import com.springjpa.models.User;
import com.springjpa.repo.CityRepository;
import com.springjpa.repo.GladiatorialSchoolRepository;
import com.springjpa.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/schools")
public class GladiatorialSchoolController {

    @Autowired
    GladiatorialSchoolRepository repository;

    @Autowired
    UserRepository userRepository;

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<GladiatorialSchool> getAll() {
        return repository.findAll();
    }

    @RequestMapping(value = "findOne/{id}", method = RequestMethod.GET)
    public GladiatorialSchool findOne(@PathVariable("id") Integer id) {
        return repository.findOne(id);
    }


    @RequestMapping(value = "save",method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public GladiatorialSchool create(@RequestBody GladiatorialSchool resource) {
       /* Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        if(userRepository.findByUsername(username).getMaster().equals(null)==false){
            User master=userRepository.findByUsername(username);
            master.setMoney(master.getMoney()-100);
            userRepository.save(master);
        }*/
       return repository.save(resource);
    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") Integer id) {
        repository.delete(id);
    }

    @RequestMapping(value = "update/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable( "id" ) Integer id, @RequestBody GladiatorialSchool resource) {
        resource.setId(id);
        repository.save(resource);
    }

    @RequestMapping(value = "getCrudGladiatorialSchool", method = RequestMethod.GET)
    public Map<String,Object> getCrudGladiatorialSchool() {
        Map<String, Object> map = new HashMap<>();
        int index = 0;
        for (GladiatorialSchool gladschool:repository.findAll()) {
            map.put("name"+index,gladschool.getName());
            map.put("city"+index,gladschool.getCity().getName());
            index++;
        }
    return map;
    }


    @RequestMapping(value = "findSchoolByName/{name}", method = RequestMethod.GET)
    public GladiatorialSchool findSchoolByName(@PathVariable("name") String name){
        return repository.findByName(name);
    }

    @RequestMapping(value = "findSchoolNames", method = RequestMethod.GET)
    public ArrayList<String> findSchoolNames(){
        ArrayList<String>list=new ArrayList<>();
        for (GladiatorialSchool school:repository.findAll()) {
            list.add(school.getName());
        }
        return list;
    }


}
