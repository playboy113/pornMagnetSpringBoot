package com.flowers.mapper;

import com.flowers.entity.flower;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author 10460
* @description 针对表【flowlists】的数据库操作Mapper
* @createDate 2023-07-18 22:34:00
* @Entity generator.domain.Flowlists
*/
@Mapper
public interface FlowlistsMapper {

    List<flower> selectAll() ;



}
