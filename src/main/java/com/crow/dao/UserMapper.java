package com.crow.dao;

import com.crow.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface UserMapper {

    @Insert("insert into user (`openid`,`session_key`,`unionid`,`username`,`sex`) " +
            "values(#{openid},#{session_key},#{unionid},#{username},#{sex})")
    void insert(User user);

    @Select("SELECT * FROM `user` " +
            "WHERE openid = #{openid}")
    List<User> getUser(@Param("openid") String openid);
}
