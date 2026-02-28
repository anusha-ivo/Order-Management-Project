package com.example.product_service.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class InventoryRepository {

    private final JdbcTemplate jdbcTemplate;

    public InventoryRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(UUID productId, int quantity) {


        String sql ="insert into inventory(product_id, available_qty)values(?,?)";
        jdbcTemplate.update(sql,productId,quantity);
    }
}
