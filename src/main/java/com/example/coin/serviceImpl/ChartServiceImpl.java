package com.example.coin.serviceImpl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.example.coin.Converter.ChartConverter;
import com.example.coin.data.ChartMapper;
import com.example.coin.po.Chart;
import com.example.coin.service.ChartService;
import com.example.coin.utils.ConstantPropertiesUtils;
import com.example.coin.vo.ChartVO;
import com.example.coin.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ChartServiceImpl implements ChartService {
    @Autowired
    ChartMapper chartMapper;

    private ChartVO saveFiles(int id,MultipartFile[] files){
        ChartVO chartVO=new ChartVO();
        if (files.length != 2) {
            return null;
        }
        String endpoint = ConstantPropertiesUtils.ENDPOINT;
        String accessKeyId = ConstantPropertiesUtils.KEYID;
        String accessKeySecret = ConstantPropertiesUtils.KEYSECRET;
        String bucketName = ConstantPropertiesUtils.BUCKETNAME;
        try {
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

            // 上传文件流。
            InputStream jsonInputStream = files[0].getInputStream();
            InputStream imgInputStream = files[1].getInputStream();

            //获取文件名称
            String jsonFileOriginalName = files[0].getOriginalFilename();
            String imgFileOriginalName = files[1].getOriginalFilename();
            //1.由于文件名重复会覆盖，生成随机文件名
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            String jsonFileName = uuid + jsonFileOriginalName;
            String imgFileName = uuid + imgFileOriginalName;
            //2.把文件按照类型+日期分类
            String datePath = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
            jsonFileName = "chartJson/" + datePath + "/" + jsonFileName+".json";
            imgFileName = "chartImg/" + datePath + "/" + imgFileName+".png";

            //第二个参数：上传到oss的文件路径和文件名称
            ossClient.putObject(bucketName, jsonFileName, jsonInputStream);
            ossClient.putObject(bucketName, imgFileName, imgInputStream);

            // 关闭OSSClient。
            ossClient.shutdown();

            //把上传之后oss返回的文件url返回（）
            String jsonUrl = "https://" + bucketName + "." + endpoint + "/" + jsonFileName;
            String imgUrl = "https://" + bucketName + "." + endpoint + "/" + imgFileName;
            chartVO = new ChartVO();
            chartVO.setUserId(id);
            chartVO.setJsonName(jsonFileOriginalName);
            chartVO.setJsonURL(jsonUrl);
            chartVO.setImgName(imgFileOriginalName);
            chartVO.setImgURL(imgUrl);
        }catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return chartVO;
    }


    @Override
    public ResponseVO saveChart(int id, MultipartFile[] files) {
        ChartVO chartVO=new ChartVO();
        try {
//                Chart chart=new Chart();
//                chart.setUserId(chartVO.getUserId());
//                chart.setImgURL(chartVO.getImgURL());
//                chart.setJsonURL(chartVO.getJsonURL());
//                chart.setImgName(chartVO.getImgName());
//                chart.setJsonName(chartVO.getJsonName());
            chartVO=this.saveFiles(id,files);
            if(chartVO==null)return ResponseVO.failure("上传失败！若文件符合规范，请联系管理员！");
            Chart chart = ChartConverter.INSTANCE.v2p(chartVO);
            chartMapper.addChart(chart);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseVO.failure("上传失败！请检查文件与网络！");
        }
        return ResponseVO.success(chartVO);
    }

    public ResponseVO updateChart(int id ,int chartId,MultipartFile[] files){
        ChartVO chartVO=new ChartVO();
        try {
//                Chart chart=new Chart();
//                chart.setUserId(chartVO.getUserId());
//                chart.setImgURL(chartVO.getImgURL());
//                chart.setJsonURL(chartVO.getJsonURL());
//                chart.setImgName(chartVO.getImgName());
//                chart.setJsonName(chartVO.getJsonName());
            chartVO=this.saveFiles(id,files);
            if(chartVO==null)return ResponseVO.failure("上传失败！若文件符合规范，请联系管理员！");
            chartVO.setChartId(chartId);
            Chart chart = ChartConverter.INSTANCE.v2p(chartVO);
            chartMapper.updateChart(chart);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseVO.failure("上传失败！请检查文件与网络！");
        }
        return ResponseVO.success(chartVO);
    }



    @Override
    public List<ChartVO> getAllCharts() {
        try {
            return ChartConverter.INSTANCE.p2v(chartMapper.getAllCharts());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<ChartVO> getUserCharts(int userId) {
        try {
            List<Chart> chartList=chartMapper.getUserCharts(userId);

//            for(int i=0;i<chartList.size();i++){
//                ChartVO chartVO=new ChartVO();
//                chartVO.setUserId(chartList.get(i).getUserId());
//                chartVO.setJsonURL(chartList.get(i).getJsonURL());
//                chartVO.setImgURL(chartList.get(i).getImgURL());
//                chartVO.setImgName(chartList.get(i).getImgName());
//                chartVO.setJsonName(chartList.get(i).getJsonName());
//                chartVOList.add(chartVO);
//            }
            return ChartConverter.INSTANCE.p2v(chartList);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ChartVO getChartById(int id) {
        return null;
    }

    @Override
    public ResponseVO deleteChart(int id) {
        try{
            chartMapper.deleteChart(id);
        }catch (Exception e){
            return ResponseVO.failure("不存在该知识图谱！");
        }
        return ResponseVO.success("知识图谱删除成功！");
    }
}
