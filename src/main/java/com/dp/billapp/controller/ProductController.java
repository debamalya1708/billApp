package com.dp.billapp.controller;

import com.dp.billapp.config.JwtResponse;
import com.dp.billapp.model.Login;
import com.dp.billapp.model.Product;
import com.dp.billapp.model.User;
import com.dp.billapp.model.UserConstants;
import com.dp.billapp.repository.UserRepository;
import com.dp.billapp.service.ProductService;
import io.vavr.control.Option;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value="/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping("/save")
    public ResponseEntity<?> saveProduct(@RequestBody Product product){
        Option<Product> productOption = productService.findBySerialNo(product.getSerialNo());
        if(!productOption.isEmpty())
            return new ResponseEntity<>("Product already exists!", HttpStatus.BAD_REQUEST);

        return ResponseEntity.ok(productService.saveProduct(product));
    }

    @GetMapping("/search/{serialNo}")
    public ResponseEntity<?> findProductBySerialNo(@PathVariable String serialNo){
        Option<Product> productOption = productService.findBySerialNo(serialNo);
        if(productOption.isEmpty())
            return new ResponseEntity<>("Product Not exists!", HttpStatus.NOT_FOUND);

        return ResponseEntity.ok(productOption);
    }


}
