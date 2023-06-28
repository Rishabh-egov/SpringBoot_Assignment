package com.example.crud.service;

import com.example.crud.repository.UserRepository;
import com.example.crud.web.models.User;
import com.example.crud.web.models.UserSearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;
    public User searchUser(UserSearchCriteria criteria){
        return this.repository.searchUser(criteria);
    }
    public List<User> getAll()
    {
        return this.repository.getAll();
    }
    public User createUser(User user)
    {

        return this.repository.createUser(user);
    }
    public String deleteUser(int id)
    {

        return this.repository.deleteUser(id);
    }
    public User updateUser(User user)
    {

        return this.repository.updateUser(user);
    }
}
