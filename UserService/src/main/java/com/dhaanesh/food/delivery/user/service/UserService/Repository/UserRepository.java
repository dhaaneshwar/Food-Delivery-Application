package com.dhaanesh.food.delivery.user.service.UserService.Repository;

import com.dhaanesh.food.delivery.user.service.UserService.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
