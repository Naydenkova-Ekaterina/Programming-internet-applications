package com.springjpa.controller;

import com.springjpa.models.GladiatorialSchool;
import com.springjpa.models.Weapon;
import com.springjpa.repo.WeaponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/weapons")
public class WeaponController {

    @Autowired
    WeaponRepository repository;

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Weapon> getAll() {
        return repository.findAll();
    }

    @RequestMapping(value = "findOne/{id}", method = RequestMethod.GET)
    public Weapon findOne(@PathVariable("id") Integer id) {
        return repository.findOne(id);
    }


    @RequestMapping(value = "save",method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Weapon create(@RequestBody Weapon resource) {
        return repository.save(resource);
    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") Integer id) {
        repository.delete(id);
    }

    @RequestMapping(value = "update/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable( "id" ) Integer id, @RequestBody Weapon resource) {
        resource.setId(id);
        repository.save(resource);
    }

    @RequestMapping(value = "getInfoWeaponById/{id}", method = RequestMethod.GET)
    public Map<String,Object> getInfoWeaponById(@PathVariable("id") Integer id){
        Map<String,Object>map=new HashMap<>();
        Weapon weapon=repository.findOne(id);
        map.put("name",weapon.getName());
        map.put("damage",weapon.getDamage());
        map.put("cost",weapon.getCost());
        return map;
    }

    @RequestMapping(value = "getCrudWeapon", method = RequestMethod.GET)
    public Map<String,Object> getCrudWeapon(){
        Map<String,Object>map=new HashMap<>();
        int index=0;
        for (Weapon weapon:repository.findAll()) {
            map.put("name"+index,weapon.getName());
             map.put("damage"+index,weapon.getDamage());
            map.put("cost"+index,weapon.getCost());
            index++;
        }
        return map;
    }

    @RequestMapping(value = "findByPicture/{pic}", method = RequestMethod.GET)
    public Weapon findByPicture(@PathVariable("pic")byte[] pic){
        Weapon weaponFound=new Weapon();
        for (Weapon weapon:repository.findAll()) {
            if(weapon.getPicture().equals(pic)){
                weaponFound=weapon;
            }
        }
     return weaponFound;
    }

    @RequestMapping(value = "getInfoWeaponForCardById/{id}", method = RequestMethod.GET)
    public Map<String,Object>  getInfoWeaponForCardById(@PathVariable("id") Integer id){
        Weapon weapon=repository.findOne(id);
        Map<String,Object>map=new HashMap<>();
        map.put("pic",weapon.getPicture());
        map.put("name",weapon.getName());
        map.put("damage",weapon.getDamage());
        map.put("cost",weapon.getCost());
        return map;
    }

    @RequestMapping(value = "findWeaponByName/{name}", method = RequestMethod.GET)
    public Weapon findWeaponByName(@PathVariable("name") String name){
        return repository.findByName(name);
    }

    @RequestMapping(value = "findWeaponNames", method = RequestMethod.GET)
    public ArrayList<String> findWeaponNames(){
        ArrayList<String>list=new ArrayList<>();
        for (Weapon weapon:repository.findAll()) {
            list.add(weapon.getName());
        }
        return list;
    }

}
