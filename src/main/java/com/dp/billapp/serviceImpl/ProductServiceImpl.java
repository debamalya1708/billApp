package com.dp.billapp.serviceImpl;

import com.dp.billapp.daoService.ProductDaoService;
import com.dp.billapp.model.Product;
import com.dp.billapp.repository.ProductRepository;
import com.dp.billapp.service.ProductService;
import io.vavr.control.Option;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    @Autowired
    private final ProductDaoService productDaoService;

    @Override
    public Product saveProduct(Product product) {
        return productDaoService.saveProduct(product);
    }

    @Override
    public Option<Product> findBySerialNo(String serialNo) {
        return productDaoService.findBySerialNo(serialNo);
    }
}
