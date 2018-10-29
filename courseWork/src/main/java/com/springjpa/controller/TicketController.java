package com.springjpa.controller;

import com.springjpa.models.Ticket;
import com.springjpa.repo.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    @Autowired
    TicketRepository repository;

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Ticket> getAll() {
        return repository.findAll();
    }

    @RequestMapping(value = "findOne/{id}", method = RequestMethod.GET)
    public Ticket findOne(@PathVariable("id") Integer id) {
        return repository.findOne(id);
    }

    @RequestMapping(value = "save",method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Ticket create(@RequestBody Ticket resource) {
        return repository.save(resource);
    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") Integer id) {
        repository.delete(id);
    }

    @RequestMapping(value = "update/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable( "id" ) Integer id, @RequestBody Ticket resource) {
        resource.setId(id);
        repository.save(resource);
    }

    @RequestMapping(value = "minCost/{minCost}", method = RequestMethod.GET)
    public List<Ticket> findByCostIsGreaterThanEqual(@PathVariable("minCost") Integer minCost) {
        return repository.findByCostIsGreaterThanEqual(minCost);
    }

    @RequestMapping(value = "maxCost/{maxCost}", method = RequestMethod.GET)
    public List<Ticket> findByCostIsLessThanEqual(@PathVariable("maxCost") Integer maxCost) {
        return repository.findByCostIsLessThanEqual(maxCost);
    }

    @RequestMapping(value = "findFightByArenaName/{arena}", method = RequestMethod.GET)
    public List<Ticket> findTicketByArenaName(@PathVariable("arena") String arena) {
        List<Ticket> list=new ArrayList<>();
        for (Ticket ticket:repository.findAll()) {
            if(ticket.getFight().getArena().getName().equals(arena)){
                list.add(ticket);
            }
        }
        return list;
    }

    @RequestMapping(value = "findTicketByCityName/{city}", method = RequestMethod.GET)
    public List<Ticket> findTicketByCityName(@PathVariable("city") String city) {
        List<Ticket> list=new ArrayList<>();
        for (Ticket ticket:repository.findAll()) {
            if(ticket.getFight().getArena().getCity().getName().equals(city)){
                list.add(ticket);
            }
        }
        return list;
    }


    @RequestMapping(value = "findTopByFightOrderByCost/{id}",method = RequestMethod.GET)
    public Ticket findTopByFight_IdOrderByCost(@PathVariable("id") Integer id){
        return repository.findTopByFight_IdOrderByCost(id);
    }

    @RequestMapping(value = "findTopByFight_IdOrderByCostDesc/{id}",method = RequestMethod.GET)
    public Ticket findTopByFight_IdOrderByCostDesc(@PathVariable("id") Integer id){
        return repository.findTopByFight_IdOrderByCostDesc(id);
    }


    @RequestMapping(value = "findByFightId/{id}", method = RequestMethod.GET)
    public ArrayList<Ticket> findByFightId(@PathVariable("id") Integer id) {
       ArrayList<Ticket> arrayList= repository.findTicketsByUserIsNull();
        ArrayList<Ticket> result=new ArrayList<>();
        for (Ticket t:arrayList) {
            if(t.getFight().getId()==id){
                result.add(t);
            }
        }
        return result;
    }


    @RequestMapping(value = "getCrudTickets", method = RequestMethod.GET)
    public Map<String,Object> getCrudTickets() {
        Map<String, Object> map = new HashMap<>();
        int index = 0;
        for (Ticket ticket:repository.findAll()) {
            map.put("cost"+index,ticket.getCost());
            map.put("fight"+index,ticket.getFight().getId());
            map.put("row"+index,ticket.getRow());
            map.put("seat"+index,ticket.getSeat());
            index++;
        }
        return map;
    }

    @RequestMapping(value = "getInfoArmorForCardById/{id}", method = RequestMethod.GET)
    public Map<String,Object>  getInfoArmorForCardById(@PathVariable("id") Integer id){
        Ticket ticket=repository.findOne(id);
        Map<String,Object>map=new HashMap<>();
        map.put("pic",ticket.getFight().getArena().getPicture());
        map.put("fightNumber",ticket.getFight().getId());
        map.put("arena",ticket.getFight().getArena().getName());
        map.put("row",ticket.getRow());
        map.put("seat",ticket.getSeat());
        map.put("cost",ticket.getCost());
        return map;
    }

    @RequestMapping(value = "findTicketsForSale", method = RequestMethod.GET)
    public ArrayList<Ticket> findTicketsForSale(){
        return repository.findTicketsByUserIsNull();
    }


}
