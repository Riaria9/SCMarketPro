package com.example.demo.Respositories;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.Tables.User;

public interface UserRepository extends CrudRepository<User, Long>
{
    public User findByEmail(String email);
    public User findByUsername(String username);

}
