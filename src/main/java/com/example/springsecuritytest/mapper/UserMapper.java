package com.example.springsecuritytest.mapper;

import com.example.springsecuritytest.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * description :
 */
@Mapper
@Repository
public interface UserMapper {
    Optional<User> selectUserByName(String username);

    void insertUser(User user);

    Boolean isExistUser(String username);

    Boolean isExistEmail(String email);
}
