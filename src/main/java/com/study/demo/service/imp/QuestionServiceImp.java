package com.study.demo.service.imp;

import com.study.demo.bean.User;
import com.study.demo.dao.UserMapper;
import com.study.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("questionService")
public class QuestionServiceImp implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void insert(User user) {
         userMapper.insertSelective(user);
    }

    @Override
    public User findByToken(String token) {
        return userMapper.selectByToken(token);
    }
}
