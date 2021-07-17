package com.example.coin.service;

import com.example.coin.vo.DataVO;
import com.example.coin.vo.ResponseVO;

import java.io.IOException;
import java.util.ArrayList;

public interface KgService {
    ResponseVO getTriple(DataVO dataVO);

    ResponseVO getExample(DataVO dataVO);

    ArrayList<ArrayList<String>> getDic();

    Boolean fuse(String a,String b) throws IOException;

    ResponseVO getFusion(DataVO dataVO) throws IOException;

    ResponseVO getAnswer(DataVO dataVO) throws IOException;
}
