package com.example.work_space_link.Controller;

import com.example.work_space_link.ApiResponse.ApiResponse;
import com.example.work_space_link.Model.User;
import com.example.work_space_link.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/work-spase-link/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/get")
    public ResponseEntity<?> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.status(200).body(users);
    }


    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestBody  User user) {
        userService.addUser(user);
        return ResponseEntity.status(200).body(new ApiResponse("User added successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable int id, @RequestBody  User user) {

        userService.updateUser(id, user);
        return ResponseEntity.status(200).body(new ApiResponse("User updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
        return ResponseEntity.status(200).body(new ApiResponse("User deleted successfully"));
    }



}
