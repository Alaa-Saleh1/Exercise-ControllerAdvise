package com.example.work_space_link.Repository;

import com.example.work_space_link.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findUserById(int id);

}
