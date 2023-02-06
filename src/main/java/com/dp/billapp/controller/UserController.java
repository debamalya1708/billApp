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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        else if(userOptional.get().getRole().equals(UserConstants.CustomerRole)){
            return new ResponseEntity<>("Not authorized to use this app", HttpStatus.UNAUTHORIZED);
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
    public ResponseEntity<?> register(@RequestBody User user){
        Option<User> userOptional = userRepository.findByContact(user.getContact());
        Option<User> singleUser = userRepository.findByEmail(user.getEmail());
        boolean isValidEmail = userService.isEmailValid(user.getEmail());

        if(!isValidEmail){
            return new ResponseEntity<>("Invalid Email!", HttpStatus.NOT_ACCEPTABLE);
        }


        if(!userOptional.isEmpty()){
            return new ResponseEntity<>("user with given contact no. already exists!", HttpStatus.BAD_REQUEST);
        }



        if(!singleUser.isEmpty()){
            return new ResponseEntity<>("user with given email already exists", HttpStatus.BAD_REQUEST);
        }


        System.out.println(user);

        long id = 0;

        if(!user.getPassword().equals("")){
            user.setRole(UserConstants.EditorRole);
        }
        else{
            user.setRole(UserConstants.CustomerRole);
        }
        id = userService.saveUser(user).getId();


        if(id > 0){
            return new ResponseEntity<>("Registered",HttpStatus.OK);
        }
        return new ResponseEntity<>("Something Went wrong, Try again!",HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/admin/register")
    public ResponseEntity<?> registerAdmin(@RequestBody User user){
        Option<User> userOptional = userRepository.findByContact(user.getContact());
        Option<User> singleUser = userRepository.findByEmail(user.getEmail());

        if(!userOptional.isEmpty()){
            return new ResponseEntity<>("admin with given contact no. already exists", HttpStatus.BAD_REQUEST);
        }
        if(!singleUser.isEmpty()){
            return new ResponseEntity<>("admin with given email already exists", HttpStatus.BAD_REQUEST);
        }

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
