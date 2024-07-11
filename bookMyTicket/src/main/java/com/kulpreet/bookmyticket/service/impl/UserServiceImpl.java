package com.kulpreet.bookmyticket.service.impl;

import com.kulpreet.bookmyticket.dao.UserDao;
import com.kulpreet.bookmyticket.exception.ValidationException;
import com.kulpreet.bookmyticket.model.User;
import com.kulpreet.bookmyticket.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.kulpreet.bookmyticket.exception.CustomExceptionCodes.NO_SUCH_USER;
import static com.kulpreet.bookmyticket.exception.CustomExceptionCodes.USER_ALREADY_EXIST;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public Long register(User user) {
        validateCreateUser(user);
        return userDao.save(user);
    }

    private void validateCreateUser(User user) {
        Long count = userDao.countByUsernameOrEmailOrMobile(user);
        if(count>0) {
            throw new ValidationException(USER_ALREADY_EXIST);
        }
    }

    @Override
    public void update(User user) {
        validateEditUser(user);
        int updatedRecords = userDao.update(user);
        log.info("Updated records: {}", updatedRecords);
    }

    private void validateEditUser(User user) {
        Long count = userDao.countById(user.getId());
        if(count == 0) {
            throw new ValidationException(NO_SUCH_USER.getCode(), NO_SUCH_USER.getMessage()+user.getId());
        }
        count = userDao.countByUsernameOrEmailOrMobileAndNotId(user);
        if(count>0) {
            throw new ValidationException(USER_ALREADY_EXIST);
        }
    }

    @Override
    public User findById(Long userId) {
        return userDao.findById(userId);
    }
}


