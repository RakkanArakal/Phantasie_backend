package com.phantasie.demo.repository;

import com.phantasie.demo.entity.UserVerify;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserVerifyRepository extends JpaRepository<UserVerify, Integer> {
    @Query(value = "select uv from UserVerify uv where uv.username = :username")
    UserVerify checkUser(@Param("username") String username);
}
