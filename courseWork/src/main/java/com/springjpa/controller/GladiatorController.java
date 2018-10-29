package com.springjpa.controller;

import com.springjpa.jabber.Jabber;
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
@RequestMapping("/gladiators")
public class GladiatorController {

    @Autowired
    GladiatorRepository repository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    WeaponRepository weaponRepository;

    @Autowired
    ArmorRepository armorRepository;

    @Autowired
    FightRepository fightRepository;

    @Autowired
    GladiatorialSchoolRepository gladiatorialSchoolRepository;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Iterable<Gladiator> getAll() {
        return repository.findAll();
    }

    @RequestMapping(value = "findOne/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Gladiator findOne(@PathVariable("id") Integer id) {
        return repository.findOne(id);
    }


    @RequestMapping(value = "save",method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Gladiator create(@RequestBody Gladiator resource) {
        return repository.save(resource);
    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") Integer id) {
        repository.delete(id);
    }

    @RequestMapping(value = "update/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable( "id" ) Integer id, @RequestBody Gladiator resource) {
        resource.setId(id);
        repository.save(resource);
    }

    @RequestMapping(value = "name/{name}", method = RequestMethod.GET)
    public Gladiator fetchDataByName(@PathVariable("name") String name){
        return repository.findByName(name);
    }

    @RequestMapping(value = "getNumberOfWins/{id}", method = RequestMethod.GET)
    public Integer getNumberOfWins(@PathVariable( "id" ) Integer id){
        int numberOfWins=0;
        Gladiator gladiator=repository.findOne(id);
        for (Fight fight:fightRepository.findAll()) {
            if(fight.getWinner().equals(gladiator.getName())){
                numberOfWins++;
            }
        }
        return numberOfWins;
    }

    @RequestMapping(value = "findInfoGladiatorById/{id}", method = RequestMethod.GET)
    public Map<String,Object> findInfoGladiatorById(@PathVariable( "id" ) Integer id){
        Map<String,Object>map=new HashMap<>();
        Gladiator gladiator=repository.findOne(id);
        map.put("name",gladiator.getName());
        map.put("age",gladiator.getAge());
        int numberOfWins=getNumberOfWins(id);
        map.put("numberOfWins",numberOfWins);
        map.put("weapon",gladiator.getWeapon().getName());
        map.put("armor",gladiator.getArmor().getName());
        map.put("gladiatorialSchool",gladiator.getGladiatorialSchool().getName());
        map.put("job",gladiator.getJob().getName());
        map.put("cost",gladiator.getCost());
        return map;
    }

    @RequestMapping(value = "getCrudGladiator", method = RequestMethod.GET)
    public Map<String,Object> getCrudGladiator(){
        Map<String,Object>map=new HashMap<>();
        int index=0;
        for (Gladiator gladiator:repository.findAll()) {
            map.put("name"+index,gladiator.getName());
            map.put("age"+index,gladiator.getAge());
            map.put("gladiatorialSchool"+index,gladiator.getGladiatorialSchool().getName());
            map.put("weapon"+index,gladiator.getWeapon().getName());
            map.put("armor"+index,gladiator.getArmor().getName());
            map.put("master"+index,gladiator.getMaster().getName());
            map.put("job"+index,gladiator.getJob().getName());
            map.put("cost"+index,gladiator.getCost());
            index++;
        }
        return map;
    }

    @RequestMapping(value = "getInfoGladiatorForCardById/{id}", method = RequestMethod.GET)
    public Map<String,Object>  getInfoGladiatorForCardById(@PathVariable("id") Integer id){
        Gladiator gladiator=repository.findOne(id);
        Map<String,Object>map=new HashMap<>();
        map.put("pic",gladiator.getPicture());
        map.put("name",gladiator.getName());
        map.put("age",gladiator.getAge());
        map.put("armor",gladiator.getArmor().getName());
        map.put("weapon",gladiator.getWeapon().getName());
        map.put("cost",gladiator.getCost());
        return map;
    }

    @RequestMapping(value = "findByPicture/{pic}", method = RequestMethod.GET)
    public Gladiator findByPicture(@PathVariable("pic")byte[] pic){
        Gladiator gladiatorFound=new Gladiator();
        for (Gladiator gladiator:repository.findAll()) {
            if(gladiator.getPicture().equals(pic)){
                gladiatorFound=gladiator;
            }
        }
        return gladiatorFound;
    }

    @RequestMapping(value = "buyWeapon/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public void buyWeapon( @PathVariable("id") Integer id) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        User user=userRepository.findByUsername(username);
        Gladiator gladiator=userRepository.findByUsername(username).getGladiator();
        Weapon weapon=weaponRepository.findOne(id);

        if(user.getMoney()>weapon.getCost()) {
            user.setMoney(user.getMoney() - weapon.getCost());
            gladiator.setWeapon(weapon);
        }
       repository.save(gladiator);
        userRepository.save(user);
        String message="You bought a new weapon - "+weapon.getName();
        JavaMail.sendMessage(user.getEmail(),message);
    }

    @RequestMapping(value = "buyArmor/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public void buyArmor(@PathVariable("id") Integer id) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        User user=userRepository.findByUsername(username);
        Gladiator gladiator=userRepository.findByUsername(username).getGladiator();
        Armor armor=armorRepository.findOne(id);
        if(user.getMoney()>armor.getCost()) {
            user.setMoney(user.getMoney() - armor.getCost());
            gladiator.setArmor(armor);
        }
        repository.save(gladiator);
        userRepository.save(user);
        String message="You bought a new armor - "+armor.getName();
        JavaMail.sendMessage(user.getEmail(),message);
    }

    @RequestMapping(value = "findGladiatorsForSale", method = RequestMethod.GET)
    public List<Gladiator> findGladiatorsForSale(){
        List<Gladiator>list=new ArrayList<>();
        for (Gladiator glad:repository.findAll()) {
            if((glad.isForSale()==true) || (glad.getMaster()==null)){
                list.add(glad);
            }
        }
        return list;
    }

    @RequestMapping(value = "applyToSchool/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void applyToSchool(@PathVariable("id") Integer id) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        Gladiator gladiator=userRepository.findByUsername(username).getGladiator();
        GladiatorialSchool gladiatorialSchool=gladiatorialSchoolRepository.findOne(id);
        gladiator.setGladiatorialSchool(gladiatorialSchool);
        int bonusToDamage=20;
       // gladiator.getWeapon().setDamage(gladiator.getWeapon().getDamage()+bonusToDamage);
        repository.save(gladiator);
        Jabber.sendJabber("Вы поступили в гладиаторскую школу:"+gladiatorialSchool.getName(), gladiator.getJabberaddress());
    }

    @RequestMapping(value = "test", method = RequestMethod.GET)
    public void test(){
        Jabber.sendJabber("Вы поступили в гладиаторскую школу:", "Ekaterina98@jabber.ru");

    }

    @RequestMapping(value = "findNagibator", method = RequestMethod.GET)
    public Gladiator  findNagibator(){

        String nagibator=null;
        int numberOfWins ;
        int max=0;
        for (Gladiator gladiator:repository.findAll()) {
            numberOfWins=0;
            for (Fight fight : fightRepository.findAll()) {
                if (fight.getWinner().equals(gladiator.getName())) {
                    numberOfWins++;
                }
            }
            if(numberOfWins>max){
                max=numberOfWins;
                nagibator=gladiator.getName();
            }
        }
        return repository.findByName(nagibator);
    }

    @RequestMapping(value = "getBoughtWeapon", method = RequestMethod.GET)
    public Map<String,Object> getBoughtWeapon() {
        Map<String, Object> map = new HashMap<>();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName(); //get logged in username
        Gladiator gladiator=userRepository.findByUsername(username).getGladiator();
        Weapon weapon=gladiator.getWeapon();
        map.put("pic",weapon.getPicture());
        map.put("name",weapon.getName());
        map.put("damage",weapon.getDamage());
        map.put("cost",weapon.getCost());

        return map;
    }

    @RequestMapping(value = "getBoughtArmor", method = RequestMethod.GET)
    public Map<String,Object> getBoughtArmor() {
        Map<String, Object> map = new HashMap<>();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName(); //get logged in username
        Gladiator gladiator=userRepository.findByUsername(username).getGladiator();
        Armor armor=gladiator.getArmor();
        map.put("name",armor.getName());
        map.put("shield",armor.getShield());
        map.put("defense",armor.getDefense());
        map.put("cost",armor.getCost());

        return map;
    }

    @RequestMapping(value = "sellArmor", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void sellArmor() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user=userRepository.findByUsername(username);
        Gladiator gladiator=userRepository.findByUsername(username).getGladiator();
        user.setMoney(user.getMoney()+gladiator.getArmor().getCost());

        User admin=userRepository.findByUsername("admin");
        admin.setMoney(admin.getMoney()+gladiator.getArmor().getCost()/10);
        userRepository.save(admin);

        gladiator.setArmor(null);
        repository.save(gladiator);
        userRepository.save(user);

    }

    @RequestMapping(value = "sellWeapon", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void sellWeapon() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user=userRepository.findByUsername(username);
        Gladiator gladiator=userRepository.findByUsername(username).getGladiator();
        user.setMoney(user.getMoney()+gladiator.getWeapon().getCost());

        User admin=userRepository.findByUsername("admin");
        admin.setMoney(admin.getMoney()+gladiator.getWeapon().getCost()/10);
        userRepository.save(admin);

        gladiator.setWeapon(null);
        repository.save(gladiator);
        userRepository.save(user);
    }

    @RequestMapping(value = "findGladiatorNames", method = RequestMethod.GET)
    public ArrayList<String> findGladiatorNames(){
        ArrayList<String>list=new ArrayList<>();
        for (Gladiator gladiator:repository.findAll()) {
            list.add(gladiator.getName());
        }
        return list;
    }

    @RequestMapping(value = "findGladiatorWeapon", method = RequestMethod.GET)
    public Weapon findGladiatorWeapon(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user=userRepository.findByUsername(username);
        Weapon weapon=new Weapon();
        if(user.getGladiator()!=null){
            weapon=user.getGladiator().getWeapon();
        }
        return weapon;
    }

    @RequestMapping(value = "findGladiatorArmor", method = RequestMethod.GET)
    public Armor findGladiatorArmor(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user=userRepository.findByUsername(username);
        Armor armor=new Armor();
        if(user.getGladiator()!=null){
            armor=user.getGladiator().getArmor();
        }
        return armor;
    }



  /*  public String sortByWins(){
        String answer="";

        Set<Gladiator> gladiators=new HashSet<>();
        for (Gladiator glad: repository.findAll()) {
            gladiators.add(glad);
        }

        for (Gladiator gl:gladiators) {
            Gladiator glBoss=findNagibator();
            answer+=glBoss.getName();
        }
        gladiators.remove()

        return answer;
    }
*/

    @RequestMapping(value = "sortByWins", method = RequestMethod.GET)
    public String sortByWins(){
        String answer="";
        for(Gladiator g:repository.getAllByNameNotNullOrderByWins()){
        answer+=g.getName()+"\n";
        }
        return answer;
    }

}
