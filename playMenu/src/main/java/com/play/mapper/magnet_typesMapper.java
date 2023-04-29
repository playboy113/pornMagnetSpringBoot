package com.play.mapper;


import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author 10460
* @description 针对表【magnet_type2】的数据库操作Mapper
* @createDate 2023-04-25 22:09:32
* @Entity generator.domain.MagnetType2
*/
@Mapper
public interface magnet_typesMapper {
    List<String> selectAllTypes();

    List<String> selectVideosByType(String type);







}
