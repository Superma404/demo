package com.study.demo.service;

import com.study.demo.bean.User;
import org.springframework.stereotype.Service;


public interface UserService {

    void insert(User user);

    User findByToken(String token);
}
