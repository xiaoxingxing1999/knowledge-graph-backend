package com.example.coin.controller;

import com.example.coin.po.User;
import com.example.coin.vo.*;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class UserControllerTest {
    @Autowired
    UserController userController;

    static UserVO userVO;
    static String email;
    static LoginVO loginVO;

    @BeforeClass
    public static void init() {
        userVO = new UserVO();
        loginVO = new LoginVO();

        userVO.setEmail("test@gmail.com");
        userVO.setPassword("123456");
        userVO.setUsername("registerTest");

        email = "181250192@smail.nju.edu.cn";

        loginVO.setEmail("123@qq.com");
        loginVO.setPassword("123123");
    }

    @Transactional
    @Test
    public void registerTest1() {
        ResponseVO responseVO = userController.register(userVO);
        String res = responseVO.getRes();
        String objEmail = ((User) responseVO.getObj()).getEmail();
        Assert.assertEquals("success", res);
        Assert.assertEquals(userVO.getEmail(), objEmail);
    }

    @Test
    public void registerTest2() {
        ResponseVO responseVO = userController.register(null);
        String res = responseVO.getRes();
        String msg = responseVO.getMsg();
        Assert.assertEquals("failure", res);
        Assert.assertEquals("source is null", msg);
    }

    @Test
    public void codeTest1() {
        ResponseVO responseVO = userController.code(email);
        String res = responseVO.getRes();
        Assert.assertEquals("success", res);
    }

    @Test
    public void codeTest2() {
        ResponseVO responseVO = userController.code(null);
        String res = responseVO.getRes();
        String msg = responseVO.getMsg();
        Assert.assertEquals("failure", res);
        Assert.assertEquals("source is null", msg);
    }

    @Test
    public void loginTest1() {
        ResponseVO responseVO = userController.login(loginVO);
        String res = responseVO.getRes();
        String objEmail = ((User) responseVO.getObj()).getEmail();
        Assert.assertEquals("success", res);
        Assert.assertEquals(loginVO.getEmail(), objEmail);
    }

    @Test
    public void loginTest2() {
        ResponseVO responseVO = userController.login(null);
        String res = responseVO.getRes();
        String msg = responseVO.getMsg();
        Assert.assertEquals("failure", res);
        Assert.assertEquals("source is null", msg);
    }
}
