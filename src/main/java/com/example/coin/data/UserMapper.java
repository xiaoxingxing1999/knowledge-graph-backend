package com.example.coin.data;

import com.example.coin.po.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {
    int addUser(User user);
    int update(User user);
    User getUserInfoById(int id);
    User getUserInfo(String email);
}
