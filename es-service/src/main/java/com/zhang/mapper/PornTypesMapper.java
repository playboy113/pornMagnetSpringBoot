package com.zhang.mapper;


import com.zhang.entity.PornTypes;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PornTypesMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table magnet_type2
     *
     * @mbggenerated Tue Jan 10 22:25:41 CST 2023
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table magnet_type2
     *
     * @mbggenerated Tue Jan 10 22:25:41 CST 2023
     */
    int insert(PornTypes record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table magnet_type2
     *
     * @mbggenerated Tue Jan 10 22:25:41 CST 2023
     */
    int insertSelective(PornTypes record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table magnet_type2
     *
     * @mbggenerated Tue Jan 10 22:25:41 CST 2023
     */
    PornTypes selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table magnet_type2
     *
     * @mbggenerated Tue Jan 10 22:25:41 CST 2023
     */
    int updateByPrimaryKeySelective(PornTypes record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table magnet_type2
     *
     * @mbggenerated Tue Jan 10 22:25:41 CST 2023
     */
    int updateByPrimaryKey(PornTypes record);

    List<PornTypes> selectAll();
}