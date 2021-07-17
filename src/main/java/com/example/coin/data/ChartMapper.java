package com.example.coin.data;

import com.example.coin.po.Chart;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ChartMapper {

    List<Chart> getAllCharts();

    List<Chart> getUserCharts(@Param("userId") int userId);

    int addChart(Chart chart);

    int updateChart(Chart chart);

    Chart getChartById(@Param("chartId") int chartId);

    int deleteChart(@Param("chartId") int chartId);
}
