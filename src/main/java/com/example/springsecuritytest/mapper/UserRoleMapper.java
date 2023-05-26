package com.example.springsecuritytest.mapper;

import com.example.springsecuritytest.model.UserRole;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * description :
 */
@Mapper
@Repository
public interface UserRoleMapper {
    void insertUserRole(UserRole userRole);
}
