package com.example.crud.utils;

import com.example.crud.web.models.Address;
import com.example.crud.web.models.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Timestamp;
import java.util.Scanner;
import java.util.UUID;

@Component
public class UserUtil {

    JdbcTemplate jdbcTemplate;
    ObjectMapper objectMapper;
    @Value("${api.url}")
    private String urlString;

    @Autowired
    public UserUtil(JdbcTemplate jdbcTemplate, ObjectMapper objectMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.objectMapper = objectMapper;
    }

    /**
     *
     * @param user
     * @return
     */
    public boolean validUser(User user)
    {
        String sql = "SELECT COUNT(*) from userTable where userName = ? and mobileNumber = ?";
        int matchCnt = jdbcTemplate.queryForObject(sql,Integer.class,user.getName(),user.getNumber());
        return (matchCnt==0);
    }

    /**
     *
     * @param attribute
     * @param urlString
     * @return
     * @throws IOException
     */
    public JsonNode getAttributeUrl(String attribute,String urlString) throws IOException {
        URL url = new URL(urlString);
        URLConnection conn = url.openConnection();
        InputStream is = conn.getInputStream();
        Scanner scanner = new Scanner(is);
        String req = null;
        while (scanner.hasNextLine())
        {
            req = scanner.nextLine();
        }
        JsonNode rootNode = objectMapper.readTree(req);
        return rootNode.get(attribute);
    }

    /**
     *
     * @param urlString
     * @return
     * @throws IOException
     */
    public Address getAddress(String urlString) throws IOException {

        JsonNode addressNode = this.getAttributeUrl("address",urlString);
        return objectMapper.treeToValue(addressNode,Address.class);
    }

    /**
     *
     * @param user
     * @return
     * @throws IOException
     */
    public User enrichUser(User user) throws IOException {

        // enrich with address , UUID , Time
        user.setAddress(this.getAddress(urlString));
        user.setId(UUID.randomUUID());
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        user.setCreatedTime(timestamp.getTime());

        return user;
    }
}
