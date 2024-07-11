package com.kulpreet.bookmyticket.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kulpreet.bookmyticket.dto.CreateUserDto;
import com.kulpreet.bookmyticket.dto.EditUserDto;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class User {
    private Long id;
    private String username;
    private String email;
    private String mobile;
    private String gender;
    private int age;

    public User(CreateUserDto createUserDto) {
        this.username = createUserDto.getUsername();
        this.email = createUserDto.getEmail();
        this.mobile = createUserDto.getMobile();
        this.gender = createUserDto.getGender();
        this.age = createUserDto.getAge();
    }

    public User(EditUserDto editUserDto) {
        this.id = editUserDto.getId();
        this.username = editUserDto.getUsername();
        this.email = editUserDto.getEmail();
        this.mobile = editUserDto.getMobile();
        this.gender = editUserDto.getGender();
        this.age = editUserDto.getAge();
    }
}

