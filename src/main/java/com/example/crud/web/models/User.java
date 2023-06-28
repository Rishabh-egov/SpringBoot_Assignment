package com.example.crud.web.models;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class User {
    private int id;
    private String name;
    private String gender;
    private String number;
    private String address;   // make a separate class for address

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", number='" + number + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
