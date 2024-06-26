package com.siemens.SpringBootAPI.models;


import jakarta.persistence.Column;
import lombok.Data;

@Data
public class UserDetails {

    private Integer UserId;

    private String userName;

    private String userEmail;

    private String contactNo;
}
