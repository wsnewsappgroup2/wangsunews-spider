package com.crow.dao;

import com.crow.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

@Mapper
public interface UserMapper {

    @Insert("insert into user (`openid`,`session_key`,`unionid`,`username`) values(#{openid},#{session_key},#{unionid},#{username})")
    void insert(User user);
}
