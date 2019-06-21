package com.study.demo.service;

import com.study.demo.bean.User;


public interface QuestionService {

    void insert(User user);

    User findByToken(String token);
}
