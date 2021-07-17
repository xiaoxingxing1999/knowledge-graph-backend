package com.example.coin.Converter;


import com.example.coin.po.Chart;
import com.example.coin.vo.ChartVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ChartConverter {
    ChartConverter INSTANCE = Mappers.getMapper(ChartConverter.class);

    
    @Mappings({
            @Mapping(target = "variable", ignore = true)
    })
    Chart v2p(ChartVO chartVO);

    ChartVO p2v(Chart user);

    List<Chart> v2p(List<ChartVO> userVOs);

    List<ChartVO> p2v(List<Chart> users);
}
