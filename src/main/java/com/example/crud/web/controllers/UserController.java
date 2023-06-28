package com.example.crud.web.controllers;

import com.example.crud.service.UserService;
import com.example.crud.web.models.User;
import com.example.crud.web.models.UserSearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
     private UserService service;

     @RequestMapping(value = "/_create", method = RequestMethod.POST)
     public User requestCreateUser(@RequestBody User user){

         return this.service.createUser(user);
     }

     @RequestMapping(value = "/_search", method = RequestMethod.GET)
     public User requestSearchUser(@RequestBody  UserSearchCriteria criteria){
         return this.service.searchUser(criteria);
     }

     @RequestMapping(value = "/_update", method = RequestMethod.PATCH)
     public User requestUpdateUser(@RequestBody  User user){
         return this.service.updateUser(user);
     }
     @RequestMapping(value = "/_delete/{id}", method = RequestMethod.DELETE)
     public String requestDeleteUser(@PathVariable int id){

         return this.service.deleteUser(id);
     }

     @RequestMapping(value = "/_getAll",method = RequestMethod.GET)
     public List<User> getAll()
     {
         return this.service.getAll();
     }
}
