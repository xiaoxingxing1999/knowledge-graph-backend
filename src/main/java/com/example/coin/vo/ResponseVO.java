package com.example.coin.vo;

import java.util.HashMap;
import java.util.Map;

public class ResponseVO {
    String res;
    String msg;
    Object obj;

    public String getRes() {
        return res;
    }

    public void setRes(String res) {
        this.res = res;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }


    public static ResponseVO success(String msg) {
        ResponseVO responseVO = new ResponseVO();
        responseVO.setRes("success");
        responseVO.setMsg(msg);
        return responseVO;
    }

    public static ResponseVO failure(String msg) {
        ResponseVO responseVO = new ResponseVO();
        responseVO.setRes("failure");
        responseVO.setMsg(msg);
        return responseVO;
    }

    public static ResponseVO success(Object obj) {
        ResponseVO responseVO = new ResponseVO();
        responseVO.setRes("success");
        responseVO.setObj(obj);
        return responseVO;
    }
}
