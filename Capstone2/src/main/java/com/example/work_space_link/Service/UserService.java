package com.example.work_space_link.Service;

import com.example.work_space_link.ApiResponse.ApiException;
import com.example.work_space_link.Model.User;
import com.example.work_space_link.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    public void addUser(User user) {
        userRepository.save(user);
    }

    public void updateUser(Integer id, User user) {
        User oldUser = userRepository.findUserById(id);
        if (oldUser == null) {
            throw new ApiException("*User not found*");
        }
        oldUser.setUsername(user.getUsername());
        oldUser.setPassword(user.getPassword());
        oldUser.setEmail(user.getEmail());
        oldUser.setPhoneNumber(user.getPhoneNumber());
        userRepository.save(oldUser);
    }

    public void deleteUser(Integer id) {
        User user = userRepository.findUserById(id);
        if(user == null) {
            throw new ApiException("*User not found*");
        }
        userRepository.delete(user);
    }
}
