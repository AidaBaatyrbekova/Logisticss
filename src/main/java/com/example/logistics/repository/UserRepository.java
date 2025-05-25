package com.example.logistics.repository;

import com.example.logistics.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Телефон номери боюнча колдонуучуну издөө
    Optional<User> findUserByPhoneNumber(String phoneNumber);

    // Телефон номери мурунтан бар экенин текшерүү
    boolean existsByPhoneNumber(String phoneNumber);

    // 1. Аты  боюнча өспүрөт сорттоо
    List<User> findAllByOrderByNameAsc();

    // 2. Роль боюнча өспүрөт сорттоо
    List<User> findAllByOrderByRoleAsc();
}