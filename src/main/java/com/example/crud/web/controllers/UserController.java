package com.example.crud.web.controllers;

import com.example.crud.service.UserService;
import com.example.crud.web.models.User;
import com.example.crud.web.models.UserSearchCriteria;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
     private UserService service;

     @RequestMapping(value = "/_create", method = RequestMethod.POST)
     public List<User> requestCreateUser(@RequestBody List<User> user) throws JsonProcessingException {

         return this.service.createUser(user);
     }

     @RequestMapping(value = "/_search", method = RequestMethod.GET)
     public List<User> requestSearchUser(@RequestBody  UserSearchCriteria criteria){
         return this.service.searchUser(criteria);
     }

     @RequestMapping(value = "/_update", method = RequestMethod.PATCH)
     public List<User> requestUpdateUser(@RequestBody  List<User> user) throws JsonProcessingException {
         return this.service.updateUser(user);
     }
     @RequestMapping(value = "/_delete/{id}", method = RequestMethod.DELETE)
     public String requestDeleteUser(@PathVariable UUID id){

         return this.service.deleteUser(id);
     }

     @RequestMapping(value = "/_getAll",method = RequestMethod.GET)
     public List<User> getAll()
     {
         return this.service.getAll();
     }
}
