package com.example.crud.service;

import com.example.crud.repository.UserRepository;
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

    @Autowired
    private UserRepository repository;

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
            User currUser = this.repository.createUser(userList.get(i));
            if(currUser != null)
            {
                users.add(currUser);
            }
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
            User currUser = this.repository.updateUser(user);
            if(currUser != null)
            {
                users.add(user);
            }
        }
        return  users;
    }
}
