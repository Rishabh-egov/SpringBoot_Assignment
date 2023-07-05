package com.example.crud.web.models;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class User {

    private UUID id;
    private boolean isActive;
    private String name;
    private String gender;
    private String number;
    private Address address;
    private long createdTime;


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", isActive=" + isActive +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", number='" + number + '\'' +
                ", address=" + address +
                ", createdTime=" + createdTime +
                '}';
    }
}
