package com.project1.dao;

import com.project1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserDAO extends JpaRepository<User, Integer> {
    @Query("select count(p) = 1 from User p where email = ?1 and userName = ?2")
    public boolean userExist(String email, String userName);
    @Query("select u from User u where email like ?1%")
    List<User> getUserByEmail(String email);

    public Optional<User> findByUserName(String username);

}
