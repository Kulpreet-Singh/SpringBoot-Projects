package com.kulpreet.bookmyticket.dao;

import com.kulpreet.bookmyticket.model.User;

import java.util.List;

public interface UserDao {
    User findById(Long id);

    Long countById(Long id);

    List<User> findByUsernameOrEmailOrMobile(User user);

    Long countByUsernameOrEmailOrMobile(User user);

    Long countByUsernameOrEmailOrMobileAndNotId(User user);

    Long save(User user);

    int update(User user);

}
