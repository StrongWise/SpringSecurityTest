package com.example.springsecuritytest.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * description :
 */
@Mapper
@Repository
public interface TestMapper {
    String hello();
}
