package com.example.coin.service;

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
public class UserServiceTest {
    @Autowired
    UserService userService;

    static UserVO userVO1;
    static UserVO userVO2;
    static String email;
    static LoginVO loginVO1;
    static LoginVO loginVO2;

    @BeforeClass
    public static void init() {
        userVO1 = new UserVO();
        userVO2 = new UserVO();
        loginVO1 = new LoginVO();
        loginVO2 = new LoginVO();

        userVO1.setEmail("test2@gmail.com");
        userVO1.setPassword("123456");
        userVO1.setUsername("registerTest");

        userVO2.setEmail("123@qq.com");
        userVO2.setPassword("123456");
        userVO2.setUsername("registerTest");

        email="181250192@smail.nju.edu.cn";

        loginVO1.setEmail("123@qq.com");
        loginVO1.setPassword("123123");

        loginVO2.setEmail("123@qq.com");
        loginVO2.setPassword("123456");
    }

    @Transactional
    @Test
    public void registerTest1() {
        ResponseVO responseVO = userService.addAccount(userVO1);
        String res = responseVO.getRes();
        String objEmail = ((User) responseVO.getObj()).getEmail();
        Assert.assertEquals("success", res);
        Assert.assertEquals(userVO1.getEmail(), objEmail);
    }

    @Test
    public void registerTest2() {
        ResponseVO responseVO = userService.addAccount(userVO2);
        String res = responseVO.getRes();
        String msg = responseVO.getMsg();
        Assert.assertEquals("failure", res);
        Assert.assertEquals("该邮箱已被使用！", msg);
    }

    @Test
    public void codeTest1() {
        ResponseVO responseVO = userService.sendCode(email);
        String res = responseVO.getRes();
        Assert.assertEquals("success", res);
    }

    @Test
    public void codeTest2() {
        ResponseVO responseVO = userService.sendCode(null);
        String res = responseVO.getRes();
        String msg = responseVO.getMsg();
        Assert.assertEquals("failure", res);
        Assert.assertEquals("source is null", msg);
    }

    @Test
    public void loginTest1() {
        ResponseVO responseVO = userService.verifyPwd(loginVO1);
        String res = responseVO.getRes();
        String objEmail = ((User) responseVO.getObj()).getEmail();
        Assert.assertEquals("success", res);
        Assert.assertEquals(loginVO1.getEmail(), objEmail);
    }

    @Test
    public void loginTest2() {
        ResponseVO responseVO = userService.verifyPwd(loginVO2);
        String res = responseVO.getRes();
        String msg = responseVO.getMsg();
        Assert.assertEquals("failure", res);
        Assert.assertEquals("login failure", msg);
    }
}
