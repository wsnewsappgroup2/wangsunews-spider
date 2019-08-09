package com.crow.dao;

import com.crow.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface UserMapper {

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into user (`openid`,`session_key`) " +
            "values(#{openid},#{session_key})")
    void insert(User user);

    @Select("SELECT * FROM `user` " +
            "WHERE openid = #{openid}")
    List<User> getUser(@Param("openid") String openid);

    @Update("UPDATE `user` " +
            "SET session_key= #{session_key} " +
            "WHERE openid=#{openid}")
    void updateSesssionKeyByOpenId(@Param("openid") String openId,
                                   @Param("session_key") String sessionKey);
}
