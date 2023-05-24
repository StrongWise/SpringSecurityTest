package com.example.springsecuritytest.mapper;

import com.example.springsecuritytest.model.ERole;
import com.example.springsecuritytest.model.Role;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * description :
 */
@Mapper
@Repository
public interface RoleMapper {
    Optional<Role> selectRoleByName(ERole name);

    void insertRole(Role role);
}
