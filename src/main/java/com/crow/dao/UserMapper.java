package com.crow.dao;

import com.crow.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface UserMapper {

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("INSERT INTO `user` (`openid`,`token`,`session_key`) " +
            "values(#{openId},#{token},#{sessionKey})")
    void insertUserBasicInfo(
            @Param("openId") String openId,
            @Param("token") String token,
            @Param("sessionKey") String sessionKey);

    @Select("SELECT * FROM `user` " +
            "WHERE openid = #{openid}")
    List<User> getUser(@Param("openid") String openid);

    @Update("UPDATE `user` " +
            "SET session_key= #{session_key} " +
            "WHERE openid=#{openid}")
    void updateSesssionKeyByOpenId(@Param("openid") String openId,
                                   @Param("session_key") String sessionKey);

    @Insert("INSERT INTO `user_column_mapping`(`openid`,`label_id`) VALUES(#{openId},#{labelId})")
    void inserDefaultUserColumnMapping(
            @Param("openId") String openId,
            @Param("labelId") Integer labelId);
}
