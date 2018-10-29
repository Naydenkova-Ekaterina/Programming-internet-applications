package com.springjpa.controller;

import com.springjpa.models.*;
import com.springjpa.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserRepository repository;

    @Autowired
    UserRoleRepository userRoleRepository;

    @Autowired
    MasterRepository masterRepository;

    @Autowired
    GladiatorRepository gladiatorRepository;

    @Autowired
    TicketRepository ticketRepository;

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<User> getAll() {
        return repository.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public User findOne(@PathVariable("id") Integer id) {
        return repository.findOne(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void create(@RequestBody User resource) {
        resource.setEnabled(true);
        User user= repository.save(resource);
        UserRole userRole=new UserRole(resource.getUsername(),"ROLE_USER");
        userRole.setUser(user);
        userRoleRepository.save(userRole);

    }

    private Set<User> userSet=new HashSet<>();

    @RequestMapping(value = "applyToBecomeMaster",method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.CREATED)
    public void applyToBecomeMaster(@RequestBody Master resource) {
        masterRepository.save(resource);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName(); //get logged in username
        User user=repository.findByUsername(username);
        user.setMaster(resource);
        userSet.add(user);

    }

    @RequestMapping(value = "getUsersForConfirming" ,method = RequestMethod.GET)
    public Map<String,Object> getUsersForConfirming() {
        Map<String, Object> map = new HashMap<>();
        int index=0;
        for (User user:userSet) {
            map.put("username"+index,user.getUsername());
            map.put("name"+index,user.getMaster().getName());
            map.put("title"+index,user.getMaster().getTitle());
            map.put("money"+index,user.getMoney());
            index++;
        }
        return map;
    }

    @RequestMapping(value = "confirmBecomeMaster",method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.CREATED)
    public void confirmBecomeMaster( @RequestBody String masterUsername) {

        User user=repository.findByUsername(masterUsername);
        UserRole userRole=new UserRole(masterUsername,"ROLE_MASTER");
        userRole.setUser(user);
        userRoleRepository.save(userRole);

        userSet.remove(user);

    }

    @RequestMapping(value = "becomeGladiator",method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.CREATED)
    public void becomeGladiator( @RequestBody Gladiator resource) {
        resource.setCost(200);
        gladiatorRepository.save(resource);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName(); //get logged in username
        User user=repository.findByUsername(username);
        UserRole userRole=new UserRole(username,"ROLE_GLADIATOR");
        user.setGladiator(resource);
        repository.save(user);
        userRole.setUser(user);
        userRoleRepository.save(userRole);
    }

    @RequestMapping(value = "becomeMaster",method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.CREATED)
    public void becomeMaster( @RequestBody Master resource) {
        masterRepository.save(resource);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName(); //get logged in username
        User user=repository.findByUsername(username);
        UserRole userRole=new UserRole(username,"ROLE_MASTER");
        user.setMaster(resource);
        repository.save(user);
        userRole.setUser(user);
        userRoleRepository.save(userRole);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") Integer id) {
        repository.delete(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable( "id" ) Integer id, @RequestBody User resource) {
        resource.setId(id);
        repository.save(resource);
    }

    @RequestMapping(value = "buyTicket/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void buyTicket(@PathVariable( "id" ) Integer id) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName(); //get logged in username

        User user=repository.findByUsername(username);
        Ticket ticket=ticketRepository.findOne(id);
        if(user.getMoney()>=ticket.getCost()) {
            user.setMoney(user.getMoney() - ticket.getCost());
            user.getTicketSet().add(ticket);
            repository.save(user);
            ticket.setUser(user);
            ticketRepository.save(ticket);
            User admin = repository.findByUsername("admin");
            admin.setMoney(admin.getMoney() + ticket.getCost());
            repository.save(admin);
        }
        else {
            System.out.println("Не хватает денег");
        }

    }

    @RequestMapping(value = "getBoughtTickets", method = RequestMethod.GET)
    public  ArrayList<Ticket> getBoughtTickets() {
        Map<String, Object> map = new HashMap<>();
        ArrayList<Ticket> arrayList=new ArrayList<>();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        User user=repository.findByUsername(username);
        int index = 0;
        for (Ticket ticket:user.getTicketSet()) {
            arrayList.add(ticket);
            //map.put("fight"+index,ticket.getFight().getId());
            //map.put("row"+index,ticket.getRow());
            //map.put("seat"+index,ticket.getSeat());
            //index++;
        }

        return arrayList;
    }

    public String findByMasterIsNotNullOrderByMoney(){

        List<User> list=new ArrayList<>();
        list.addAll(repository.findByMasterIsNotNullOrderByMoney());
        String answer="";

        for (User user:list) {
            answer+=user.getMaster().getName()+"\n";
        }

        return answer;
    }


}
