package com.springjpa.controller;

import com.springjpa.models.Armor;
import com.springjpa.models.Weapon;
import com.springjpa.repo.ArmorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This controller provides CRUD operations for working with entity "Armor"
 */
@RestController
@RequestMapping("/armors")
public class ArmorController {

    /**
     *
     */
    @Autowired
    ArmorRepository repository;

    /**
     *For getting all armors
     * @return armor list
     */
    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Armor> getAll() {
        return repository.findAll();
    }

    /**
     *For getting an armor by id
     * @param id armor id
     * @return Armor
     */
    @RequestMapping(value = "findOne/{id}", method = RequestMethod.GET)
    public Armor findOne(@PathVariable("id") Integer id) {
        return repository.findOne(id);
    }

    /**
     *For adding an armor to database
     * @param resource an armor, which will be added to DB
     * @return created armor
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Armor create(@RequestBody Armor resource) {
        return repository.save(resource);
    }

    /**
     *For deleting an armor by its id from database
     * @param id armor id
     */
    @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") Integer id) {
        repository.delete(id);
    }

    /**
     *For updating the armor
     * @param id armor id, which will be updated
     * @param resource a new armor to replace previous values
     */
    @RequestMapping(value = "update/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable( "id" ) Integer id, @RequestBody Armor resource) {
        resource.setId(id);
        repository.save(resource);
    }

    /**
     *For getting information about selected armor
     * @param id armor id
     * @return information about the armor
     */
    @RequestMapping(value = "getInfoArmorById/{id}", method = RequestMethod.GET)
    public Map<String,Object> getInfoArmorById(@PathVariable("id") Integer id){
        Map<String,Object> map=new HashMap<>();
        Armor armor=repository.findOne(id);
        map.put("name",armor.getName());
        map.put("shield",armor.getShield());
        map.put("defense",armor.getDefense());
        map.put("cost",armor.getCost());
        return map;
    }

    /**
     *For getting information about animals, which wil be shown in the table
     * @return information, which wil be shown in the table
     */
    @RequestMapping(value = "getCrudArmors", method = RequestMethod.GET)
    public Map<String,Object> getCrudArmors() {
        Map<String, Object> map = new HashMap<>();
        int index = 0;
        for (Armor armor:repository.findAll()) {
            map.put("name"+index,armor.getName());
            map.put("shield"+index,armor.getShield());
            map.put("defense"+index,armor.getDefense());
            map.put("cost"+index,armor.getCost());
            index++;
        }
        return map;
    }

    /**
     *For getting information about the armor, which will be shown in the card
     * @param id armor id
     * @return information about the armor, which will be shown in the card
     */
    @RequestMapping(value = "getInfoArmorForCardById/{id}", method = RequestMethod.GET)
    public Map<String,Object>  getInfoArmorForCardById(@PathVariable("id") Integer id){
        Armor armor=repository.findOne(id);
        Map<String,Object>map=new HashMap<>();
        map.put("pic",armor.getPicture());
        map.put("name",armor.getName());
        map.put("shield",armor.getShield());
        map.put("defense",armor.getDefense());
        map.put("cost",armor.getCost());
        return map;
    }

    /**
     *For getting armor with selected name
     * @param name armor name
     * @return armor
     */
    @RequestMapping(value = "findArmorByName/{name}", method = RequestMethod.GET)
    public Armor findArmorByName(@PathVariable("name") String name){
        return repository.findByName(name);
    }

    /**
     *
     * @param pic
     * @return
     */
    @RequestMapping(value = "findByPicture/{pic}", method = RequestMethod.GET)
    public Armor findByPicture(@PathVariable("pic")byte[] pic){
        Armor armorFound=new Armor();
        for (Armor armor:repository.findAll()) {
            if(armor.getPicture().equals(pic)){
                armorFound=armor;
            }
        }
        return armorFound;
    }

    @RequestMapping(value = "findArmorNames", method = RequestMethod.GET)
    public ArrayList<String> findArmorNames(){
        ArrayList<String>list=new ArrayList<>();
        for (Armor armor:repository.findAll()) {
            list.add(armor.getName());
        }
        return list;
    }

}
