package com.example.logistics.repository;

import com.example.logistics.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByPhoneNumber(String phoneNumber);


    boolean existsByPhoneNumber(String phoneNumber);


    List<User> findAllByOrderByNameAsc();


    List<User> findAllByOrderByRoleAsc();
}