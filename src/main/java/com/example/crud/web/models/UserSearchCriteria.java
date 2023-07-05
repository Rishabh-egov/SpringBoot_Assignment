package com.example.crud.web.models;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserSearchCriteria {

    private Integer id;
    private String number;
    private Boolean isActive;

//    public UserSearchCriteria(int id, String number, boolean isActive) {
//        this.id = null;
//        this.number = null;
//        this.isActive = null;
//    }
}
