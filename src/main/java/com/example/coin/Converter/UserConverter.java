package com.example.coin.Converter;

import com.example.coin.po.User;
import com.example.coin.vo.UserVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;


@Mapper
public interface UserConverter {
    UserConverter INSTANCE = Mappers.getMapper(UserConverter.class);

    //@Mappings({
    //@Mapping(source = "mail", target = "email"),
    //@Mapping(source = "date", target = "birthday", dateFormat = "yyyy-MM-dd HH:mm:ss"),
    //@Mapping(target = "birthExpressionFormat", expression = "java(org.apache.commons.lang3.time.DateFormatUtils.format(person.getBirthday(),\"yyyy-MM-dd HH:mm:ss\"))"),
    @Mapping(target = "id", ignore = true)
    //})
    User v2p(UserVO userVO);

    UserVO p2v(User user);

    List<User> v2p(List<UserVO> userVOs);

    List<UserVO> p2v(List<User> users);
} //调用的方式为：User user= UserConverter.INSTANCE.v2p(userVO);
