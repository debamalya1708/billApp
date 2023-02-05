package com.dp.billapp.serviceImpl;

import com.dp.billapp.daoService.UserDaoService;
import com.dp.billapp.model.User;
import com.dp.billapp.model.UserConstants;
import com.dp.billapp.repository.UserRepository;
import com.dp.billapp.service.UserService;
import io.vavr.control.Option;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    UserDaoService userDaoService;

    @Override
    public User saveUser(User user) {
        System.out.println("save user");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userDaoService.register(user);
    }

    @Override
    public List<User> getAll(String role) {
        return userDaoService.getAll(role);
    }

    @Override
    public User getUser(String contactNumber) {
        return null;
    }

    @Override
    public Option<User> findByContact(String contact) {
        return userDaoService.findByContact(contact);
    }

    @Override
    public User findById(long id) {
        return userDaoService.findById(id);
    }

}
