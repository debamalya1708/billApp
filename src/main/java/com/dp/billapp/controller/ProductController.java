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

import java.util.List;

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

        return ResponseEntity.ok(productOption.get());
    }

    @GetMapping("/search/id/{id}")
    public ResponseEntity<?> findProductBySerialNo(@PathVariable long id){
        Option<Product> productOption = productService.findById(id);
        if(productOption.isEmpty())
            return new ResponseEntity<>("Product Not exists!", HttpStatus.NOT_FOUND);

        return ResponseEntity.ok(productOption.get());
    }

    @GetMapping("/allSerialNumber")
    public ResponseEntity<?> allSerialNumbers(){
        List<String> serialNumberList = productService.getallSerialNo();
        return ResponseEntity.ok(serialNumberList);

    }

    @PutMapping("/update")
    public ResponseEntity<?> updateProduct(@RequestBody Product product){
        Option<Product> productOption = productService.findById(product.getId());
        if(productOption.isEmpty())
            return new ResponseEntity<>("Product doesn't exists,can't be updated!!!!",HttpStatus.NOT_FOUND);

        return ResponseEntity.ok(productService.updateProduct(product));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable long id){
        Option<Product> productOption = productService.findById(id);
        if(productOption.isEmpty())
            return new ResponseEntity<>("Product doesn't exists,can't be deleted!!!!",HttpStatus.NOT_FOUND);

        return  ResponseEntity.ok(productService.deleteProduct(id));
    }


}
