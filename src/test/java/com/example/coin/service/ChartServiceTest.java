package com.example.coin.service;

import com.example.coin.data.ChartMapper;
import com.example.coin.po.Chart;
import com.example.coin.serviceImpl.ChartServiceImpl;
import com.example.coin.vo.ChartVO;
import com.example.coin.vo.ResponseVO;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.LinkedList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ChartServiceTest {
    private static String SAVE_CHART_SUCCESS="成功保存该知识图谱";
    private static String SAVE_CHART_NULL_FAILURE="知识图谱信息错误，无法保存";
    @InjectMocks
    ChartServiceImpl chartServiceImpl;
    @Mock
    ChartMapper chartMapper;

    static ChartVO chartVO;

    @BeforeClass
    public static void setChart(){
        chartVO=new ChartVO();
    }

    @Test
    @Transactional
    public void saveChartTest1(){
        Chart chart=new Chart();
        Mockito.when(chartMapper.addChart(chart)).thenReturn(1);
        //MultipartFile[] multipartFiles=new MultipartFile[2];
        ResponseVO responseVO= chartServiceImpl.saveChart(0,new MultipartFile[1]);
        String res=responseVO.getRes();
        Assert.assertEquals(res,"failure");
    }

    @Test
    public void getAllChartsTest1(){
        List<Chart> chartList=new LinkedList<>();
        Chart chart=new Chart();
        chart.setJsonURL("1234");
        chart.setUserId(0);
        chart.setImgURL("1234");
        chartList.add(chart);
        Mockito.when(chartMapper.getAllCharts()).thenReturn(chartList);
        List<ChartVO> chartVOList= chartServiceImpl.getAllCharts();
        Assert.assertEquals(chartVOList.get(0).getJsonURL(),"1234");
        Assert.assertEquals(chartVOList.get(0).getImgURL(),"1234");
        Assert.assertEquals(chartVOList.get(0).getUserId(),0);
    }

    @Test
    public void getAllChartsTest2(){
        Mockito.when(chartMapper.getAllCharts()).thenReturn(null);
        List<ChartVO> chartVOList= chartServiceImpl.getAllCharts();
        Assert.assertEquals(chartVOList,null);
    }

    @Test
    public void getUserChartsTest1(){
        List<Chart> chartList=new LinkedList<>();
        Chart chart=new Chart();
        chart.setJsonURL("1234");
        chart.setUserId(0);
        chart.setImgURL("1234");
        chartList.add(chart);
        Mockito.when(chartMapper.getUserCharts(Mockito.anyInt())).thenReturn(chartList);
        List<ChartVO> chartVOList=chartServiceImpl.getUserCharts(1);
        Assert.assertEquals(chartVOList.get(0).getJsonURL(),chartList.get(0).getJsonURL());
        Assert.assertEquals(chartVOList.get(0).getImgURL(),chartList.get(0).getImgURL());
        Assert.assertEquals(chartVOList.get(0).getUserId(),chartList.get(0).getUserId());
    }

    @Test
    public void getUserChartsTest2(){
        Mockito.when(chartMapper.getUserCharts(Mockito.anyInt())).thenReturn(null);
        List<ChartVO> chartVOList=chartServiceImpl.getUserCharts(-1);
        Assert.assertEquals(chartVOList,null);

    }
}
