package com.lanxin.service.impls;

import com.lanxin.bean.Users;
import com.lanxin.bean.UsersExample;
import com.lanxin.dao.UsersMapper;
import com.lanxin.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UsersService {
    @Autowired
    private UsersMapper usersMapper;
    @Override
    public Users getUsers(Users user) {
        return usersMapper.selectByUsers(user);
    }
}
