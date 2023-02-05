package com.dp.billapp.controller;

import com.dp.billapp.config.JwtResponse;
import com.dp.billapp.helper.JwtUtil;
import com.dp.billapp.model.Login;
import com.dp.billapp.model.User;
import com.dp.billapp.model.UserConstants;
import com.dp.billapp.repository.UserRepository;
import com.dp.billapp.service.CustomUserDetailsService;
import com.dp.billapp.service.UserService;
import io.vavr.control.Option;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(value="/user")
public class UserController {


    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    private UserConstants miUserConstants;

    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Login login) throws Exception{
        Option<User> userOptional = userRepository.findByContact(login.getUserName());
        if(userOptional.isEmpty()){
            return new ResponseEntity<>("User Not found", HttpStatus.NOT_FOUND);
        }
        log.info("#  log in user with mobile - {}", login);
        try {
            this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login.getUserName(),login.getPassword()));
        }
        catch(BadCredentialsException ex) {
            return new ResponseEntity<>("Invalid credentials", HttpStatus.FORBIDDEN);
        }
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(login.getUserName());
        User user  = userService.getUser(login.getUserName());
        final String jwt = jwtUtil.generateToken(user,userDetails);
        return new ResponseEntity<>(new JwtResponse(jwt),HttpStatus.OK);
    }


    @PostMapping("/register")
    public void register(@RequestBody User user){
        System.out.println(user);

        if(!user.getPassword().equals("")){
            user.setRole(UserConstants.EditorRole);
        }
        else{
            user.setPassword(user.getContact());
            user.setRole(UserConstants.CustomerRole);
        }
        userService.saveUser(user);
    }

    @PostMapping("/admin/register")
    public ResponseEntity<?> registerAdmin(@RequestBody User user){
        Option<User> userOptional = userRepository.findByContact(user.getContact());
        if(userOptional.isEmpty()){
            return new ResponseEntity<>("contact no. with user already exists!", HttpStatus.BAD_REQUEST);
        }
//        Option<User> userEmailOptional = userRepository.findByContact(user.getEmail());
//        if(userOptional.isEmpty()){
//            return new ResponseEntity<>("contact no. with user already exists!", HttpStatus.BAD_REQUEST);
//        }
        long id = 0;
        if(user.getRole().equals(UserConstants.AdminRegKey)){
            user.setRole(UserConstants.AdminRole);
            id = userService.saveUser(user).getId();
        }
        if(id > 0){
            return new ResponseEntity<>("Registered",HttpStatus.OK);
        }
        return new ResponseEntity<>("Something Went wrong, Try again!",HttpStatus.BAD_REQUEST);
    }

}
