package com.example.springsecuritytest.repository;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * description :
 */
@Mapper
@Repository
public interface TestRepository {
    String hello();
}
