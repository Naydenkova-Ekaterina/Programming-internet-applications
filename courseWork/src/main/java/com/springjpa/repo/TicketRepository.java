package com.springjpa.repo;

import com.springjpa.models.Ticket;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.List;

public interface TicketRepository extends CrudRepository<Ticket,Integer> {
    List<Ticket> findByCostIsGreaterThanEqual(Integer minCost);
    List<Ticket> findByCostIsLessThanEqual(Integer maxCost);
    Ticket findTopByFight_IdOrderByCost(Integer id);
    Ticket findTopByFight_IdOrderByCostDesc(Integer id);
    ArrayList<Ticket> findByFight_Id(Integer id);
    ArrayList<Ticket>findTicketsByUserIsNull();
}
