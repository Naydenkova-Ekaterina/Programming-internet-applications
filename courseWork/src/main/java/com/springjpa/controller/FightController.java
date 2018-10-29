package com.springjpa.controller;

import com.springjpa.SpringJpaPostgreSqlApplication;
//import com.springjpa.jms.Email;
import com.springjpa.models.*;
import com.springjpa.repo.FightRepository;
import com.springjpa.repo.GladiatorRepository;
import com.springjpa.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
//import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/fights")
public class FightController {

    @Autowired
    FightRepository repository;

    @Autowired
    GladiatorRepository gladiatorRepository;

    @Autowired
    UserRepository userRepository;

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Fight> getAll() {
        return repository.findAll();
    }

    @RequestMapping(value = "findOne/{id}", method = RequestMethod.GET)
    public Fight findOne(@PathVariable("id") Integer id) {
        return repository.findOne(id);
    }


    @RequestMapping(value = "save",method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody Fight resource) {

        AnimalsController animalsController= SpringJpaPostgreSqlApplication.context.getBean(AnimalsController.class);
        GladiatorController gladiatorController= SpringJpaPostgreSqlApplication.context.getBean(GladiatorController.class);

        repository.save(resource);

        HashSet<Animal> animalHashSet=new HashSet<>();
        HashSet<Gladiator>gladiatorHashSet=new HashSet<>();

        System.out.println(resource.getId());

        System.out.println(resource.getAnimalEntities());
        for (Animal a:resource.getAnimalEntities()) {
            animalHashSet.add(a);
        }

        for (Gladiator g:resource.getGladiatorEntities()) {
            gladiatorHashSet.add(g);
        }

        for (Animal a:animalHashSet) {
            Animal newA=animalsController.findOne(a.getId());
            System.out.println(newA.getFightEntities());

            newA.getFightEntities().add(resource);
            animalsController.update(newA.getId(),newA);
        }

        for (Gladiator g:gladiatorHashSet) {
            Gladiator newG=gladiatorController.fetchDataByName(g.getName());
            System.out.println(newG.getFightEntities());

            newG.getFightEntities().add(resource);
            gladiatorController.update(newG.getId(),newG);
        }

/*
       JmsTemplate jmsTemplate = SpringJpaPostgreSqlApplication.context.getBean(JmsTemplate.class);

        for (User user: userRepository.findAll()) {
            jmsTemplate.convertAndSend("mailbox", new Email(user.getEmail(), "Анонс нового боя:"+resource.getId()));
        }
*/

    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") Integer id) {
        AnimalsController animalsController= SpringJpaPostgreSqlApplication.context.getBean(AnimalsController.class);
        GladiatorController gladiatorController=SpringJpaPostgreSqlApplication.context.getBean(GladiatorController.class);

        Fight f=repository.findOne(id);
        for (Animal a:f.getAnimalEntities()) {
            a.getFightEntities().remove(f);
            animalsController.update(a.getId(),a);

        }

        for (Gladiator g:f.getGladiatorEntities()) {
            g.getFightEntities().remove(f);
            gladiatorController.update(g.getId(),g);

        }


        repository.delete(id);
    }

    @RequestMapping(value = "update/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable( "id" ) Integer id, @RequestBody Fight resource) {

        AnimalsController animalsController= SpringJpaPostgreSqlApplication.context.getBean(AnimalsController.class);
        GladiatorController gladiatorController=SpringJpaPostgreSqlApplication.context.getBean(GladiatorController.class);


        Fight fight=repository.findOne(id);
        HashSet<Animal>hashSetOldA=new HashSet<>();
        for (Animal a:fight.getAnimalEntities()) {
            hashSetOldA.add(a);
        }
        for (Animal a:hashSetOldA) {
            //Weapon newW=weaponController.findWeaponByName(w.getName());
            Animal newA=animalsController.findOne(a.getId());

            newA.getFightEntities().remove(fight);
            animalsController.repository.save(newA);
        }

        HashSet<Gladiator>hashSetOldG=new HashSet<>();
        for (Gladiator g:fight.getGladiatorEntities()) {
            hashSetOldG.add(g);
        }
        for (Gladiator g:hashSetOldG) {
            //Weapon newW=weaponController.findWeaponByName(w.getName());
            Gladiator newG=gladiatorController.findOne(g.getId());

            newG.getFightEntities().remove(fight);
            gladiatorController.repository.save(newG);
        }



        resource.setId(id);
        repository.save(resource);




        HashSet<Animal>hashSetA=new HashSet<>();
        for (Animal a:resource.getAnimalEntities()) {
            hashSetA.add(a);
        }
        for (Animal a:hashSetA) {
            Animal newA=animalsController.findAnimalByName(a.getName());

            newA.getFightEntities().add(resource);
            animalsController.repository.save(newA);
        }

        HashSet<Gladiator>hashSetG=new HashSet<>();
        for (Gladiator g:resource.getGladiatorEntities()) {
            hashSetG.add(g);
        }
        for (Gladiator g:hashSetG) {
            Gladiator newG=gladiatorController.fetchDataByName(g.getName());

            newG.getFightEntities().add(resource);
            System.out.println(newG.getFightEntities());
            gladiatorController.repository.save(newG);
        }




        if(resource.getWinner().equals("")==false){
            Gladiator gladiator=gladiatorRepository.findByName(resource.getWinner());
            User user= userRepository.findByMaster(gladiator.getMaster());
            user.setMoney(user.getMoney()+resource.getPrize());
            userRepository.save(user);
            gladiator.setWins(gladiator.getWins()+1);
            gladiatorRepository.save(gladiator);
        }
    }
/*
    @RequestMapping(value = "test", method = RequestMethod.GET)
    public void test(){
        JmsTemplate jmsTemplate = SpringJpaPostgreSqlApplication.context.getBean(JmsTemplate.class);
        jmsTemplate.convertAndSend("mailbox", new Email("katandv@gmail.com", "Hello!!!!!!!!!"));

    }
*/
    @RequestMapping(value = "findGladiatorsByFightId/{id}", method = RequestMethod.GET)
    public List<Gladiator> findGladiatorsByFightId(@PathVariable( "id" ) Integer id){
        List<Gladiator>list=new ArrayList<>();
        for (Fight fight:repository.findAll()) {
            if(fight.getId().equals(id)){
                list.addAll(fight.getGladiatorEntities());
            }
        }
        return list;
    }

    @RequestMapping(value = "findAnimalsByFightId/{id}", method = RequestMethod.GET)
    public List<Animal> findAnimalsByFightId(@PathVariable( "id" ) Integer id){
        List<Animal>list=new ArrayList<>();
        for (Fight fight:repository.findAll()) {
            if(fight.getId().equals(id)){
                list.addAll(fight.getAnimalEntities());
            }
        }
        return list;
    }

    @RequestMapping(value = "findFightForCardByFightId/{id}",method = RequestMethod.GET)
    public Map<String,Object> findFightForCard(@PathVariable("id") Integer id){
        Map<String,Object> map=new HashMap<>();

            Fight fight=repository.findOne(id);
            TicketController ticketController=new TicketController();
            map.put("name",fight.getArena().getName());
            map.put("address",fight.getArena().getAddress());
            map.put("city",fight.getArena().getCity().getName());
            int index=0;
            for (Gladiator glad:findGladiatorsByFightId(id)) {
                map.put("gladiator"+index,glad.getName());
                index++;
            }
            index=0;
            for (Animal animal:findAnimalsByFightId(id)) {
                map.put("animals"+index,animal.getName());
            }
            map.put("minCost",ticketController.findTopByFight_IdOrderByCost(id).getCost());
            map.put("maxCost",ticketController.findTopByFight_IdOrderByCostDesc(id).getCost());

        return map;
    }

    @RequestMapping(value = "getCrudFights", method = RequestMethod.GET)
    public Map<String,Object> getCrudFights() {
        Map<String, Object> map = new HashMap<>();
        int index = 0;
        for (Fight fight:repository.findAll()) {
            map.put("arena"+index,fight.getArena().getName());
            map.put("prize"+index,fight.getPrize());
            map.put("winner"+index,fight.getWinner());
            index++;
        }
        return map;
    }

    @RequestMapping(value = "getIdFights", method = RequestMethod.GET)
    public ArrayList<Integer> getIdFights() {
        ArrayList<Integer> list=new ArrayList<>();
        for (Fight fight: repository.findAll()) {
            list.add(fight.getId());
        }
        return list;
    }


}
