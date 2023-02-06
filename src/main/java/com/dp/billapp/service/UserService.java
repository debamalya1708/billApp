package com.dp.billapp.service;

import com.dp.billapp.model.User;
import io.vavr.control.Option;

import java.util.List;

public interface UserService {
    User saveUser(User user);

    List<User> getAll(String role);
    User getUser(String contactNumber);
    Option<User> findByContact(String contact);
    Option<User> findByEmail(String email);
    boolean isEmailValid(String email);
    User findById(long id);

//    List<ContactResponse> searchUser(String userId,SearchRequest searchRequest);
}
