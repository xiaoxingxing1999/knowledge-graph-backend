package com.example.coin.controller;

import com.example.coin.serviceImpl.ChartServiceImpl;
import com.example.coin.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController()
@RequestMapping("/api/chart")
public class ChartController {
    @Autowired
    ChartServiceImpl chartService;


    @PostMapping("/{id}/saveChart")
    @ResponseBody
    public ResponseVO saveChart(@RequestParam MultipartFile[] files, @RequestParam String chartId,@RequestParam boolean isChartAlreadySaved, @PathVariable int id){

        if(isChartAlreadySaved){
            int intChartId=Integer.parseInt(chartId);
            return chartService.updateChart(id,intChartId,files);
        }
        else return chartService.saveChart(id,files);
    }

    @GetMapping("/getAllCharts")
    @ResponseBody
    public ResponseVO getAllCharts(){return ResponseVO.success(chartService.getAllCharts());}

    @GetMapping("/{id}/getUserCharts")
    @ResponseBody
    public ResponseVO getUserCharts(@PathVariable int id){
        return ResponseVO.success(chartService.getUserCharts(id));
    }

    @GetMapping("/{id}/getChart")
    @ResponseBody
    public ResponseVO getChart(@PathVariable int id){
        return ResponseVO.success(chartService.getChartById(id));
    }

    @GetMapping("/{id}/deleteChart")
    @ResponseBody
    public ResponseVO deleteChart(@PathVariable int id){
        return ResponseVO.success(chartService.deleteChart(id));
    }
}
