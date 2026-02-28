package com.example.product_service.repository;

import com.example.product_service.modules.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRepository {
    private final JdbcTemplate jdbcTemplate;
    public ProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

public void save(Product product){
        String sql="insert into products(product_id, stock_keeping_unit, name, description, price, currency, status)values(?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql,product.getProductId(),product.getStock_keeping_unit(),product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getCurrency(),
                product.getStatus()
        );
}

}
