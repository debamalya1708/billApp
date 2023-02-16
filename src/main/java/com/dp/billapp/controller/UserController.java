package com.dp.billapp.controller;

import com.dp.billapp.config.JwtResponse;
import com.dp.billapp.helper.JwtUtil;
import com.dp.billapp.model.Login;
import com.dp.billapp.model.Product;
import com.dp.billapp.model.User;
import com.dp.billapp.model.UserConstants;
import com.dp.billapp.repository.UserRepository;
import com.dp.billapp.service.CustomUserDetailsService;
import com.dp.billapp.service.UserService;
import io.vavr.control.Option;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
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

    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;



    @PostMapping(value = "/login")
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
//        HttpHeaders headers = new HttpHeaders();
//        headers.add(UserConstants.AuthHeader, new JwtResponse(jwt).getResponse());
//        return new ResponseEntity<>("User Authenticated",headers,HttpStatus.OK);
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

    @GetMapping("/search/{id}")
    public ResponseEntity<?> searchUserById(@PathVariable long id){
        Optional<User> userOptional = userService.findById(id);
        if(userOptional.isEmpty())
            return  new ResponseEntity<>("User Not Found!!!",HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(userOptional);

    }
    @GetMapping("/search/role/{role}")
    public ResponseEntity<?> searchUserByRole(@PathVariable String role,HttpServletRequest request){
        if(request.getContentLength()==0)
            return  new ResponseEntity<>("token Not Found!!!",HttpStatus.NOT_FOUND);
        String userContact= userService.getContact(request);
        Option<User> userOptional = userService.findByContact(userContact);
            if(!userOptional.isEmpty()){
                if((userOptional.get().getRole().equals(UserConstants.AdminRole)||
                        userOptional.get().getRole().equals(UserConstants.EditorRole))
                                && role.equals("customers")){
                    List<User> customerList = userService.getAll(UserConstants.CustomerRole);
                    return ResponseEntity.ok(customerList);
                }
                else if(userOptional.get().getRole().equals(UserConstants.AdminRole)
                        && role.equals("employees")){
                    List<User> employeeList = userService.getAll(UserConstants.EditorRole);
                    return ResponseEntity.ok(employeeList);
                }
                else if(userOptional.get().getRole().equals(UserConstants.AdminRole)
                        && role.equals("admeen")){
                    List<User> adminList = userService.getAll(UserConstants.AdminRole);
                    return ResponseEntity.ok(adminList);
                }
                else if(userOptional.get().getRole().equals(UserConstants.EditorRole)
                        && role.equals("admeen")){
                    return new ResponseEntity<>("Access Denied !!!!",HttpStatus.BAD_REQUEST);
                }
            }
            else {
                return new ResponseEntity<>("User Not found",HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>("Access Denied !!!!",HttpStatus.UNAUTHORIZED);
    }

    //will be enhanced more
    @GetMapping("/role/contact/{role}")
    public ResponseEntity<?> getContactNoByRole(@PathVariable String role){
        if(role.equals("customer") || role.equals("editor")){
            List<String> contactList = userService.getAllContacts(role);
            return ResponseEntity.ok(contactList);
        }
        return new ResponseEntity<>("Not able to get Users",HttpStatus.NOT_FOUND);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateProduct(@RequestBody User user){
        Optional<User> userOption = userService.findById(user.getId());
        if(userOption.isEmpty())
            return new ResponseEntity<>("User doesn't exists,can't be updated!!!!",HttpStatus.NOT_FOUND);

        return ResponseEntity.ok(userService.updateUser(user));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable long id){
        Optional<User> userOption = userService.findById(id);
        if(userOption.isEmpty())
            return new ResponseEntity<>("User doesn't exists,can't be deleted!!!!",HttpStatus.NOT_FOUND);

        return  ResponseEntity.ok(userService.deleteById(id));
    }

    @GetMapping("/check")
    public ResponseEntity<?> checkUser(HttpServletRequest request){
        log.info("request length: {}", request.getContentLength());
        if(request.getContentLength()==0){
            return  new ResponseEntity<>("token Not Found!!!",HttpStatus.NOT_FOUND);
        }
        log.info("request: {}", request);
        String requestHeader = request.getHeader("Authorization");
        if(requestHeader!=null && requestHeader.startsWith("Bearer ")) {
            String jwtToken = requestHeader.substring(7);
            if(jwtUtil.isTokenExpired(jwtToken)){
                return  new ResponseEntity<>("User Not Found!!!",HttpStatus.NOT_FOUND);
            }
            else if(!jwtUtil.isTokenExpired(jwtToken)){
                Map<String, String> map = jwtUtil.getJwtTokenDetails(request);
                Option<User> userOptional = userService.findByContact(map.get(UserConstants.contactNo));
                if (!userOptional.isEmpty())
                    return new ResponseEntity<>(userOptional.get(),HttpStatus.OK);
                else
                    return  new ResponseEntity<>("User Not Found!!!",HttpStatus.NOT_FOUND);
            }

        }
        return new ResponseEntity<>("No Key found",HttpStatus.UNAUTHORIZED);
    }



}
