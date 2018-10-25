package com.springjpa.controller;

import com.springjpa.Password;
import com.springjpa.model.User;
import com.springjpa.model.UserRole;
import com.springjpa.repo.UserRepository;
import com.springjpa.repo.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserRepository repository;

    @Autowired
    UserRoleRepository userRoleRepository;

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<User> getAllUsers() {

        return repository.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public User findOne(@PathVariable("id") Integer id) {
        return repository.findOne(id);
    }


    @RequestMapping(method = RequestMethod.POST, consumes= MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody User resource) throws Exception {

        String hashPassword=null;

        hashPassword=Password.generateHash(resource.getPassword());

            resource.setPassword(hashPassword);
            resource.setEnabled(true);
            UserRole userRole=new UserRole(resource.getUsername(),"ROLE_USER");

        repository.save(resource);
        userRoleRepository.save(userRole);

    }

   /* @RequestMapping(value = "/authUser", method = RequestMethod.POST)
    public Boolean findUserByUandP(@RequestBody User user){
        User user1=repository.findByUsername(user.getUsername());
        if(user1==null){
            return false;
        }
        boolean check=false;
        try {
           check= Password.check(user.getPassword(), user1.getPassword());
        }catch (Exception e)
        {
            System.err.println(e.fillInStackTrace());
        }
        return check;
    }*/

    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    public User findByUsername(@PathVariable("username") String username){

        return repository.findByUsername(username);

    }

    @RequestMapping(value = "/encodePassword", method = RequestMethod.POST)
    public String encodePassword(@RequestBody User user){
        if (user==null){
            return "error";
        }
        User   user1=repository.findByUsername(user.getUsername());
        String hashPassword=Password.generateHash(user.getPassword());
        boolean check=false;
if(!hashPassword.equals(user1.getPassword())){
    return "error";
}

        return hashPassword;
    }

}
