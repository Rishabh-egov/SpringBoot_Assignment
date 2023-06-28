package com.example.crud.repository;

import com.example.crud.web.models.User;
import com.example.crud.web.models.UserSearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public User searchUser(UserSearchCriteria criteria)
    {
        // TO DO refactor this
        User usr = jdbcTemplate.queryForObject("SELECT userId as id,userName as name,gender,mobileNumber as number ,address FROM userTable WHERE userId=? or mobileNumber=?", BeanPropertyRowMapper.newInstance(User.class) , criteria.getId(),criteria.getNumber());
        return usr;
    }
    public User createUser(User user)
    {
        int val = jdbcTemplate.update(
                "INSERT INTO userTable (userId,userName,gender,mobileNumber,address) VALUES(?,?,?,?,?)" ,
                user.getId(),user.getName(),user.getGender(),user.getNumber(),user.getAddress());
        return user;
    }
    public String deleteUser(int id)
    {
        int affected = jdbcTemplate.update(
                "DELETE FROM userTable WHERE userId=?",
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

    public User updateUser(User user)
    {
        int affected
                = jdbcTemplate.update(
                "UPDATE userTable SET userName = ? , gender = ? , mobileNumber = ? , address = ? WHERE userId = ?",
                user.getName(),user.getGender(),user.getNumber(),user.getAddress(),user.getId()
        );

        if(affected > 0)
        {
            return jdbcTemplate.queryForObject("SELECT userId as id,userName as name,gender,mobileNumber as number ,address FROM userTable WHERE userId=?",BeanPropertyRowMapper.newInstance(User.class), user.getId());
        }
        else
            return null;

//        if(affected > 0){
//
//            UserSearchCriteria criteria = new UserSearchCriteria();
//            criteria.setId(user.getId());
//            criteria.setNumber(user.getNumber());
//
//            return this.searchUser(criteria);
//        }
//        else
//        {
//            return null;
//        }

    }
    public List<User> getAll()
    {
        List<User> userList = jdbcTemplate.query("SELECT userId as id,userName as name,gender,mobileNumber as number ,address FROM userTable",new BeanPropertyRowMapper<>(User.class));
        return userList;
    }
}
