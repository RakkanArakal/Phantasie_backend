package com.phantasie.demo.repository;

import com.phantasie.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query(value = "select u from User u where u.user_id = :id")
    User findUserById(@Param("id") Integer id);

    @Query(value = "select u from User u where u.name = :name")
    User findUserByName(@Param("name") String name);
}
