package com.example.coin.controller;

import com.example.coin.po.User;
import com.example.coin.serviceImpl.UserServiceImpl;
import com.example.coin.vo.CodeVO;
import com.example.coin.vo.LoginVO;
import com.example.coin.vo.ResponseVO;
import com.example.coin.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/api/user")

public class UserController {
    @Autowired
    UserServiceImpl userService;


    @GetMapping("/{email}//code")
    @ResponseBody
    public ResponseVO code(@PathVariable String email) {
        return userService.sendCode(email);
    }

    @PostMapping("/register")
    @ResponseBody
    public ResponseVO register(@RequestBody UserVO userVO) {
        return userService.addAccount(userVO);
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseVO login(@RequestBody LoginVO loginVO) {
        return userService.verifyPwd(loginVO);
    }

    @GetMapping("/{id}/getUserInfo")
    @ResponseBody
    public ResponseVO getUserInfo(@PathVariable int id) {
        User user = userService.getUserInfo(id);
        if(user==null){
            return ResponseVO.failure("用户信息错误");
        }
        return ResponseVO.success(user);
    }

    @PostMapping("/modifyName")
    @ResponseBody
    public ResponseVO modifyName(@RequestBody UserVO userVO) {
        return userService.modifyName(userVO);
    }

    @PostMapping("/updatePassword")
    @ResponseBody
    public ResponseVO updatePassword(@RequestBody UserVO userVO) {
        return userService.modifyPassword(userVO);
    }
}
