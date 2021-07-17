package com.example.coin.service;

import com.example.coin.vo.ChartVO;
import com.example.coin.vo.ResponseVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ChartService {

    ResponseVO saveChart(int id, MultipartFile[] files);

    ResponseVO updateChart(int id,int chartId, MultipartFile[] files);

    List<ChartVO> getAllCharts();

    List<ChartVO> getUserCharts(int userId);

    ChartVO getChartById(int id);

    ResponseVO deleteChart(int id);
}
