package com.dp.billapp.service;

import com.dp.billapp.model.Product;
import io.vavr.control.Option;

public interface ProductService {
    Product saveProduct(Product product);
    Option<Product> findBySerialNo(String serialNo);
    Option<Product> findById(long id);
}
