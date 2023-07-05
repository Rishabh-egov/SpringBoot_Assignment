package com.example.crud.repository;

import com.example.crud.utils.UserUtil;
import com.example.crud.web.models.User;
import com.example.crud.web.models.UserSearchCriteria;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.*;

@Repository
public class UserRepository {


    final JdbcTemplate jdbcTemplate;
    final UserRowMapper userRowMapper;
    final ObjectMapper objectMapper;
    final UserUtil userUtil;

    @Autowired
    public UserRepository(JdbcTemplate jdbcTemplate, UserRowMapper userRowMapper, ObjectMapper objectMapper, UserUtil userUtil) {
        this.jdbcTemplate = jdbcTemplate;
        this.userRowMapper = userRowMapper;
        this.objectMapper = objectMapper;
        this.userUtil = userUtil;
    }

    /**
     *
     * @param user
     * @return
     */
    @Deprecated
    public boolean validUser(User user)
    {
        String sql = "SELECT COUNT(*) from userTable where userName = ? and mobileNumber = ?";
        int matchCnt = jdbcTemplate.queryForObject(sql,Integer.class,user.getName(),user.getNumber());
        return (matchCnt==0);
    }

    /**
     *
     * @param criteria
     * @return
     */
    public List<User> searchUser(UserSearchCriteria criteria)
    {
        boolean isId = criteria.getId()!=null;
        boolean isNumber = criteria.getNumber()!=null;
        boolean isActive = criteria.getIsActive()!=null;
        List<Object> params = new ArrayList<>();
        String sql = "SELECT userId,username,gender,mobileNumber,address,isActive,createdTime From userTable WHERE 1=1";

        if(isId)
        {
            sql += " AND userId = ?";
            params.add(criteria.getId());
        }
        if(isNumber)
        {
            sql += " AND mobileNumber = ?";
            params.add(criteria.getNumber());
        }
        if(isActive)
        {
            sql += " AND isActive = ?";
            params.add(criteria.getIsActive());
        }

        return jdbcTemplate.query(sql, userRowMapper,params.toArray());
    }

    /**
     *
     * @param user
     * @return
     * @throws JsonProcessingException
     */
    public User createUser(User user) throws JsonProcessingException {

        if(!this.userUtil.validUser(user))
        {
            return null;
        }

        try {
            user = this.userUtil.enrichUser(user);
            String addressJson = objectMapper.writeValueAsString(user.getAddress());

            int val = jdbcTemplate.update(
                    "INSERT INTO userTable (userId , userName , gender , mobileNumber , address , isActive,createdTime ) VALUES(?,?,?,?,?::json,?,?)" ,
                    user.getId(),user.getName(),user.getGender(),user.getNumber(),addressJson,user.isActive(),user.getCreatedTime());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return user;
    }

    /**
     *
     * @param userList
     * @return
     * @throws JsonProcessingException
     */

    @Deprecated
    public List<User> createUserList(List<User> userList) throws JsonProcessingException {
        List<User> users = new ArrayList<User>();
        for(int i=0;i<userList.size();i++)
        {
            User currUser = this.createUser(userList.get(i));
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
        int affected = jdbcTemplate.update(
                "DELETE FROM userTable WHERE userId = ?",
                id
        );
        if(affected > 0)
        {
            return "User deleted successfully";
        }
        else
        {
            return "User not found";
        }
    }

    /**
     *
     * @param user
     * @return
     * @throws JsonProcessingException
     */
    public User updateUser(User user) throws JsonProcessingException {

        String jsonAddress = objectMapper.writeValueAsString(user.getAddress());

        int affected
                = jdbcTemplate.update(
                "UPDATE userTable SET userName = ? , gender = ? , mobileNumber = ? , address = ?::json, isActive = ? WHERE userId = ?",
                user.getName(),user.getGender(),user.getNumber(),jsonAddress,user.isActive(),user.getId()
        );

        if(affected > 0)
        {
            return jdbcTemplate.queryForObject("SELECT userId ,userName ,gender, mobileNumber ,address ,isActive,createdTime FROM userTable WHERE userId=?",userRowMapper, user.getId());
        }
        else
            return null;
    }

    /**
     *
     * @param userList
     * @return
     * @throws JsonProcessingException
     */

    @Deprecated
    public List<User> updateUserList(List<User> userList) throws JsonProcessingException {
        List<User> users = new ArrayList<User>();
        for(User user:userList)
        {
            User currUser = this.updateUser(user);
            if(currUser!=null)
            {
                users.add(currUser);
            }
        }
        return users;
    }

    /**
     *
     * @return
     */
    public List<User> getAll()
    {
        List<User> userList = jdbcTemplate.query("SELECT * FROM userTable",userRowMapper);
        return userList;
    }
}
