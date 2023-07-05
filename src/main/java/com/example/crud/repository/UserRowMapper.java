package com.example.crud.repository;

import com.example.crud.web.models.Address;
import com.example.crud.web.models.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Component
public class UserRowMapper implements RowMapper <User>{
    ObjectMapper objectMapper;

    @Autowired
    public UserRowMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }



    public User mapRow(ResultSet rs, int row) throws SQLException {

        User user = new User();
        try {
            String  addressJson  = rs.getString("address");
            Address address = objectMapper.readValue(addressJson,Address.class);
            user.setAddress(address);
            user.setId(rs.getObject("userId", UUID.class));
            user.setName(rs.getString("userName"));
            user.setGender(rs.getString("gender"));
            user.setNumber(rs.getString("mobileNumber"));
            user.setActive(rs.getBoolean("isActive"));
            user.setCreatedTime(rs.getLong("createdTime"));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return user;
    }
}



// enrichment id , timestamp
// long epoch timeStamp
//  rowmapper
// User Array in search , update
// code formatting
// name + mobileNumber --> unique
// Call the GET api and put the address object from the response into the user during create call.
