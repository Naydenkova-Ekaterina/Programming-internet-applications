package com.springjpa.controller;

import com.springjpa.models.Arena;
import com.springjpa.models.Weapon;
import com.springjpa.repo.ArenaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/arenas")
public class ArenaController {

    @Autowired
    ArenaRepository repository;

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Arena> getAll() {
        return repository.findAll();
    }

    @RequestMapping(value = "findOne/{id}", method = RequestMethod.GET)
    public Arena findOne(@PathVariable("id") Integer id) {
        return repository.findOne(id);
    }

    @RequestMapping(value = "save",method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Arena create(@RequestBody Arena resource) {
        return repository.save(resource);
    }


    @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") Integer id) {
        repository.delete(id);
    }

    @RequestMapping(value = "update/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable( "id" ) Integer id, @RequestBody Arena resource) {
        resource.setId(id);
        repository.save(resource);
    }

    @RequestMapping(value = "getCrudArena", method = RequestMethod.GET)
    public Map<String,Object> getCrudArena() {
        Map<String,Object>map=new HashMap<>();
        int index=0;
        for (Arena arena:repository.findAll()) {
            map.put("name"+index,arena.getName());
            map.put("address"+index,arena.getAddress());
            map.put("city"+index,arena.getCity().getName());
            map.put("numberOfRows"+index,arena.getNumberOfRows());
            map.put("numberOfSeats"+index,arena.getNumberOfSeats());
            index++;
        }
        return map;
    }

    @RequestMapping(value = "findArenaNames", method = RequestMethod.GET)
    public ArrayList<String> findArenaNames(){
        ArrayList<String>list=new ArrayList<>();
        for (Arena arena:repository.findAll()) {
            list.add(arena.getName());
        }
        return list;
    }

    @RequestMapping(value = "findArenaByName/{name}", method = RequestMethod.GET)
    public Arena findArenaByName(@PathVariable("name") String name){
        return repository.findByName(name);
    }
}
