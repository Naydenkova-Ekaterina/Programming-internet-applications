package com.springjpa.controller;

import com.springjpa.SpringJpaPostgreSqlApplication;
import com.springjpa.models.Animal;
import com.springjpa.models.Fight;
import com.springjpa.models.Weapon;
import com.springjpa.repo.AnimalsRepository;
import com.springjpa.repo.WeaponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * This controller provides CRUD operations for working with entity "Animals"
 */
@RestController
@RequestMapping("/animals")
public class AnimalsController {

    /**
     *
     */
    @Autowired
    AnimalsRepository repository;


    /**
     *For getting all animals
     * @return animal list
     */
    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Animal> getAll() {
        return repository.findAll();
    }

    /** method for GET request, url:/animals/id
     *For getting an animal by id
     * @param id animal id
     * @return Animal in JSON
     */
    @RequestMapping(value = "findOne/{id}", method = RequestMethod.GET)
    public Animal findOne(@PathVariable("id") Integer id) {
        return repository.findOne(id);
    }

    /**
     *For adding an animal to database
     * @param resource an animal, which will be added to DB
     * @return created animal
     */
    @RequestMapping(value = "save",method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody Animal resource) {
        WeaponController weaponController= SpringJpaPostgreSqlApplication.context.getBean(WeaponController.class);

         repository.save(resource);
        System.out.println(resource.getName());
        System.out.println(resource.getWeaponEntities());
        HashSet<Weapon>hashSet=new HashSet<>();
        for (Weapon w:resource.getWeaponEntities()) {
            hashSet.add(w);
        }

        for (Weapon w:hashSet) {
            Weapon newW=weaponController.findWeaponByName(w.getName());
            System.out.println(newW.getAnimalEntities());

            newW.getAnimalEntities().add(resource);
            weaponController.update(newW.getId(),newW);
        }


    }

    /**
     *For deleting an animal by its id from database
     * @param id animal id
     */
    @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") Integer id) {
        WeaponController weaponController= SpringJpaPostgreSqlApplication.context.getBean(WeaponController.class);
        FightController fightController= SpringJpaPostgreSqlApplication.context.getBean(FightController.class);

        Animal a=repository.findOne(id);
        for (Weapon w:a.getWeaponEntities()) {
            w.getAnimalEntities().remove(a);
            weaponController.update(w.getId(),w);
        }
        for (Fight f:a.getFightEntities()) {
            f.getAnimalEntities().remove(a);
            fightController.update(f.getId(),f);
        }
        repository.delete(id);
    }

    /**
     *For updating the animal
     * @param id animal id, which will be updated
     * @param resource a new animal to replace previous values
     */
    @RequestMapping(value = "update/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable( "id" ) Integer id, @RequestBody Animal resource) {
///////////////////
        WeaponController weaponController= SpringJpaPostgreSqlApplication.context.getBean(WeaponController.class);


        Animal animal=repository.findOne(id);
        HashSet<Weapon>hashSetOld=new HashSet<>();
        for (Weapon w:animal.getWeaponEntities()) {
            hashSetOld.add(w);
        }
        for (Weapon w:hashSetOld) {
            Weapon newW=weaponController.findWeaponByName(w.getName());

            newW.getAnimalEntities().remove(animal);
            weaponController.repository.save(newW);
        }

        System.out.println(resource.getWeaponEntities());
        resource.setId(id);
        repository.save(resource);


        System.out.println(resource.getName());
        System.out.println(resource.getWeaponEntities());
        HashSet<Weapon>hashSet=new HashSet<>();
        for (Weapon w:resource.getWeaponEntities()) {
            hashSet.add(w);
        }
        for (Weapon w:hashSet) {
            Weapon newW=weaponController.findWeaponByName(w.getName());

            newW.getAnimalEntities().add(resource);
            weaponController.repository.save(newW);
        }


    }

    /**
     *For getting information about animals, which wil be shown in the table
     * @return information, which wil be shown in the table
     */
    @RequestMapping(value = "getCrudAnimals", method = RequestMethod.GET)
    public Map<String,Object> getCrudAnimals() {
        Map<String, Object> map = new HashMap<>();
        int index = 0;
        for (Animal animal:repository.findAll()) {
            map.put("name"+index,animal.getName());
            index++;
        }
    return map;
    }

    @RequestMapping(value = "findAnimalNames", method = RequestMethod.GET)
    public ArrayList<String> findAnimalNames(){
        ArrayList<String>list=new ArrayList<>();
        for (Animal animal:repository.findAll()) {
            list.add(animal.getName());
        }
        return list;
    }

    @RequestMapping(value = "findAnimalByName/{name}", method = RequestMethod.GET)
    public Animal findAnimalByName(@PathVariable("name") String name){
        return repository.findByName(name);
    }


}
