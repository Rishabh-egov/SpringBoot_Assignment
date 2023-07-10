package com.example.crud.consumer;

import com.example.crud.repository.UserRepository;
import com.example.crud.web.models.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer {
    private final ObjectMapper objectMapper;
    private UserRepository repository;

    @Autowired
    public Consumer(ObjectMapper objectMapper, UserRepository repository) {
        this.objectMapper = objectMapper;
        this.repository = repository;
    }

    @KafkaListener(topics = {"create"})
    public void createConsumer(String message) throws JsonProcessingException {
        User user = objectMapper.readValue(message,User.class);
        this.repository.createUser(user);
    }

    @KafkaListener(topics = {"update"})
    public void updateConsumer(String message) throws JsonProcessingException {
        User user = objectMapper.readValue(message,User.class);
        this.repository.updateUser(user);
    }
}
