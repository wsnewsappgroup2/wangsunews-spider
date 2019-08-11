package com.crow.dao;

import com.crow.entity.Algorithm;
import com.crow.entity.LabelAlgorithm;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
@Mapper
public interface UserLabelAlgorithmMapper {
 /*   *//**前端可以通过userid查出用户所有已选栏目以及对应算法*//*
    @Select("SELECT ula.`id` AS `id`," +
            "ula.`user_id` AS `user_id`," +
            "ula.`label_id` AS `label_id`," +
            "ula.`algorithm_id` AS `algorithm_id`," +
            "ula.`status` AS `status`," +
            "lcm.`column_name` AS `label`," +
            "al.`algorithm_ch` AS `algorithm`," +
            "FROM `user_label_algorithm` ula " +
            "LEFT JOIN `label_column_mapping` lcm "+
            "ON ula.label_id=lcm.label_id " +
            "LEFT JOIN `algorithm` al " +
            "ON ula.algorithm_id=al.id"+
            "WHERE `user_id`=#{userId}")
    List<UserLabelAlgorithmMapper> getUserLabelAlgorithm(@Param("userId") String userId);*/


    /**通过栏目ID查出可选的所有算法*/
    @Select("SELECT al.* FROM " +
            "`label_algorithm` la LEFT JOIN `algorithm` al ON la.algorithm_id=al.id " +
            "WHERE `label_id`=#{labelId}")
    List<Algorithm> selectCloumnedList(@Param("labelId") Integer labelId);


    @Select("SELECT COUNT(*) FROM `user_label_al` WHERE id=#{alId}")
    Integer selectAlgorithmUsedCount(@Param("alId")Integer alId);


    /**应用场景：更新用户可显示栏目/更新用户栏目对应算法*/
    @Update("UPDATE `user_label_al` " +
            "SET `algorithm_id` = #{algorithmId}  " +
            "WHERE `user_id`=#{userId} AND `label_id`=#{labelId}")
    void updateUserLabelAlgorithm(
            @Param("userId") int userId,
            @Param("labelId") int labelId,
            @Param("algorithmId") int algorithmId);


}
