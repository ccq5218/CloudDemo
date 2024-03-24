package cn.itcast.user.service;

import cn.itcast.user.pojo.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.itcast.user.mapper.UserMapper;

@Service
@Log4j2
public class UserService {
    @Autowired
    private UserMapper userMapper;
    public User queryById(Long id) {
        return userMapper.selectByPrimaryKey(id);
    }
}
