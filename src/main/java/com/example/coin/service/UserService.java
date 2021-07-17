package com.example.coin.service;

import com.example.coin.po.User;
import com.example.coin.vo.LoginVO;
import com.example.coin.vo.ResponseVO;
import com.example.coin.vo.UserVO;


public interface UserService {
    User getUserInfo(int id);
    User getUserInfo(String email);
    ResponseVO sendCode(String email);
    ResponseVO addAccount(UserVO userVO);
    ResponseVO verifyPwd(LoginVO loginVO);
}
