package generator.mapper;

import generator.domain.MagnetDb;

/**
* @author 10460
* @description 针对表【magnet_db】的数据库操作Mapper
* @createDate 2024-04-01 20:27:29
* @Entity generator.domain.MagnetDb
*/
public interface MagnetDbMapper {

    int deleteByPrimaryKey(Long id);

    int insert(MagnetDb record);

    int insertSelective(MagnetDb record);

    MagnetDb selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MagnetDb record);

    int updateByPrimaryKey(MagnetDb record);

}
