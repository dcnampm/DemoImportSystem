package com.example.demoimportsystem.daos;

import com.example.demoimportsystem.models.User;

public interface UserDAO {
    User findUserByUsername(String username);
}
