package com.dp.billapp.daoService;

import com.dp.billapp.model.Product;
import io.vavr.control.Option;


public interface ProductDaoService {

    Product saveProduct(Product product);
    Option<Product> findBySerialNo(String serialNo);
    Option<Product> findById(long id);

}
