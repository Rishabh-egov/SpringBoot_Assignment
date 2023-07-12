package com.example.crud.service;

import com.example.crud.producer.Producer;
import com.example.crud.repository.UserRepository;
import com.example.crud.utils.UserUtil;
import com.example.crud.web.models.User;
import com.example.crud.web.models.UserSearchCriteria;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private UserRepository repository;
    private Producer producer;

    private UserUtil util;

    @Autowired
    public UserService(UserRepository repository, Producer producer, UserUtil util) {
        this.repository = repository;
        this.producer = producer;
        this.util = util;
    }

    /**
     *
     * @param criteria
     * @return
     */
    public List<User> searchUser(UserSearchCriteria criteria){
        return this.repository.searchUser(criteria);
    }

    /**
     *
     * @return
     */
    public List<User> getAll()
    {
        return this.repository.getAll();
    }


    /**
     *
     * @param userList
     * @return
     * @throws JsonProcessingException
     */
    public List<User> createUser(List<User> userList) throws JsonProcessingException {


        List<User> users = new ArrayList<>();
        for(int i=0;i<userList.size();i++)
        {
            if(this.util.validUser(userList.get(i)))
                this.producer.push("create",userList.get(i));
        }
        return  users;

    }

    /**
     *
     * @param id
     * @return
     */
    public String deleteUser(UUID id)
    {

        return this.repository.deleteUser(id);
    }

    /**
     *
     * @param userList
     * @return
     * @throws JsonProcessingException
     */
    public List<User> updateUser(List<User> userList) throws JsonProcessingException {

        List<User> users = new ArrayList<>();
        for(User user : userList )
        {
            this.producer.push("update",user);
        }
        return  users;
    }
}
