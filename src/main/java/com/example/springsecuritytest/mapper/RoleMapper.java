package com.example.springsecuritytest.mapper;

import com.example.springsecuritytest.model.ERole;
import com.example.springsecuritytest.model.Role;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * description :
 */
@Mapper
@Repository
public interface RoleMapper {
    List<Role> selectRoles();
    Optional<Role> selectRoleByName(ERole name);
    Set<Role> selectUserRoleByUserId(Long userId);

    void insertRole(Role role);
}
