package com.springjpa.controller;


import com.springjpa.javaMail.JavaMail;
import com.springjpa.models.*;
import com.springjpa.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/masters")
public class MasterController {

    @Autowired
    MasterRepository repository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserRoleRepository userRoleRepository;

    @Autowired
    GladiatorRepository gladiatorRepository;

    @Autowired
    GladiatorialSchoolRepository gladiatorialSchoolRepository;

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Master> getAll() {
        return repository.findAll();
    }

    @RequestMapping(value = "findOne/{id}", method = RequestMethod.GET)
    public Master findOne(@PathVariable("id") Integer id) {
        return repository.findOne(id);
    }


    @RequestMapping(value = "save",method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody Master resource) {
         repository.save(resource);
    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") Integer id) {
        repository.delete(id);
    }

    @RequestMapping(value = "update/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable( "id" ) Integer id, @RequestBody Master resource) {
        resource.setId(id);
        repository.save(resource);
    }

    @RequestMapping(value = "getCrudMasters", method = RequestMethod.GET)
    public Map<String,Object> getCrudMasters() {
        Map<String, Object> map = new HashMap<>();
        int index = 0;
        for (User master:userRepository.findAll()) {
            map.put("name"+index,master.getMaster().getName());
            map.put("title"+index,master.getMaster().getTitle());
            map.put("money"+index,master.getMoney());
            map.put("city"+index,master.getMaster().getCity().getName());
            index++;
        }
        return map;
    }

    @RequestMapping(value = "findMasterByName/{name}", method = RequestMethod.GET)
    public Master findMasterByName(@PathVariable("name") String name){
        return repository.findByName(name);
    }

    @RequestMapping(value = "getAllGladiators/{id}", method = RequestMethod.GET)
    public Set<Gladiator> getAllGladiators(@PathVariable("id")Integer id){
        Master master=repository.findOne(id);
        return master.getGladiatorEntities();
    }

    @RequestMapping(value = "buyGladiator/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public void buyGladiator(@PathVariable( "id" ) Integer id) {
       // resource.setId(id);
       // repository.save(resource);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        User user=userRepository.findByUsername(username);

        Master master=userRepository.findByUsername(username).getMaster();
        Gladiator gladiator=gladiatorRepository.findOne(id);
        master.getGladiatorEntities().add(gladiator);
        gladiator.setMaster(master);
        gladiator.setForSale(false);
        int cost=gladiator.getCost();

        System.out.println(cost);


        if(user.getMoney()>cost) {
            user.setMoney(user.getMoney() - cost);
        }
        repository.save(master);
        gladiatorRepository.save(gladiator);

        User admin=userRepository.findByUsername("admin");
        admin.setMoney(admin.getMoney()+gladiator.getCost()/10);
        userRepository.save(admin);
        userRepository.save(user);
        String message="You bought a new gladiator - "+gladiator.getName();
       // JavaMail.sendMessage(user.getEmail(),message);
    }

    @RequestMapping(value = "sellGladiator/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void sellGladiator( @PathVariable( "id" ) Integer id) {

        Gladiator gladiator=gladiatorRepository.findOne(id);
        gladiator.setForSale(true);
        gladiatorRepository.save(gladiator);
    }

    @RequestMapping(value = "getBoughtGladiators", method = RequestMethod.GET)
    public ArrayList<Gladiator> getBoughtGladiators() {
        ArrayList<Gladiator> arrayList=new ArrayList<>();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName(); //get logged in username
        User user=userRepository.findByUsername(username);
        if(user.getMaster()!=null) {
            Master master = user.getMaster();
            for (Gladiator gladiator : master.getGladiatorEntities()) {
                arrayList.add(gladiator);
            }
        }
        return arrayList;
    }

    @RequestMapping(value = "invest/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void invest(@PathVariable( "id" ) Integer id) {
        GladiatorialSchool gladiatorialSchool=gladiatorialSchoolRepository.findOne(id);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName(); //get logged in username
        Master master=userRepository.findByUsername(username).getMaster();
        master.getGladiatorialSchoolSet().add(gladiatorialSchool);
        gladiatorialSchool.getMasterSet().add(master);

        gladiatorialSchoolRepository.save(gladiatorialSchool);
        repository.save(master);
    }

    @RequestMapping(value = "payForSchools", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void payForSchools() {
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName(); //get logged in username
        User user=userRepository.findByUsername(username);
        Master master=userRepository.findByUsername(username).getMaster();
        for (GladiatorialSchool gs: master.getGladiatorialSchoolSet()) {
            user.setMoney(user.getMoney()-gs.getContribution());
        }
        repository.save(master);
        userRepository.save(user);
    }

    @RequestMapping(value = "findMasterNames", method = RequestMethod.GET)
    public ArrayList<String> findMasterNames(){
        ArrayList<String>list=new ArrayList<>();
        for (Master master:repository.findAll()) {
            list.add(master.getName());
        }
        return list;
    }

}
