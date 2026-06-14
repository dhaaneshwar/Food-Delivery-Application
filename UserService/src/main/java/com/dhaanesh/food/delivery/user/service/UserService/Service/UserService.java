package com.dhaanesh.food.delivery.user.service.UserService.Service;

import com.dhaanesh.food.delivery.user.service.UserService.Entity.User;
import com.dhaanesh.food.delivery.user.service.UserService.Repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User register(User user) {
        return userRepository.save(user);
    }

    public String login(String email, String password) {

        Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent() &&
                user.get().getPassword().equals(password)) {
            return "Login Successful";
        }

        return "Invalid Credentials";
    }

    public User getUser(Long id) {
        // throw a ResponseStatusException so controllers return 404 instead of 500 when user is missing
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        return user;
    }

    public User updateUser(Long id, User updatedUser) {

        // throw a ResponseStatusException so controllers return 404 instead of 500 when user is missing
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        user.setName(updatedUser.getName());
        user.setEmail(updatedUser.getEmail());
        user.setPhone(updatedUser.getPhone());

        return userRepository.save(user);
    }

    public String deleteUser(Long id) {

        userRepository.deleteById(id);

        return "User Deleted Successfully";
    }
}
