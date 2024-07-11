package com.kulpreet.bookmyticket.service;

import com.kulpreet.bookmyticket.model.User;

public interface UserService {
    Long register(User user);

    void update(User user);

    User findById(Long userId);
}
