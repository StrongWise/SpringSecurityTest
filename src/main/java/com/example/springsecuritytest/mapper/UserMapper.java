package com.example.springsecuritytest.mapper;

import com.example.springsecuritytest.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * description :
 */
@Mapper
@Repository
public interface UserMapper {
    User selectUserByName(String username);

    int insertUser(User user);

    Boolean isExistUser(String username);

    Boolean isExistEmail(String email);
}
