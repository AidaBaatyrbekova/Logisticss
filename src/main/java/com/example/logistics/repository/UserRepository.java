package com.example.logistics.repository;

import com.example.logistics.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Телефон номери боюнча колдонуучуну издөө
    Optional<User> findUserByPhoneNumber(String phoneNumber);

    // Телефон номери мурунтан бар экенин текшерүү
    boolean existsByPhoneNumber(String phoneNumber);
}