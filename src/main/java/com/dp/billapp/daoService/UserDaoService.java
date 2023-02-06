package com.dp.billapp.daoService;

import com.dp.billapp.model.User;
import io.vavr.control.Option;

import java.util.List;

public interface UserDaoService {

    User register(User user);

    User getUser(String contact);

    Option<User> findByContact(String phone);

    Option<User> findByEmail(String email);

    List<User> getAll(String role);

    User findById(long id);

}
