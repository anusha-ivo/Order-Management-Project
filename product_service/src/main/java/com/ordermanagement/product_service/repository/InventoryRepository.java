package com.ordermanagement.product_service.repository;

import com.ordermanagement.product_service.modules.Inventory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class InventoryRepository {
    @Value("${inventory.insert}")
    private String insertQuery;

    @Value("${inventory.findById}")
    private String findQuery;

    @Value("${inventory.deduct}")
    private String deductQuery;

    @Value("${inventory.restore}")
    private String restoreQuery;

    private final JdbcTemplate jdbcTemplate;

    public InventoryRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void createInitialStock(Long productId, Integer qty) {
        jdbcTemplate.update(insertQuery, productId, qty);
    }
    public Optional<Inventory> findByProductId(Long productId) {

        List<Inventory> list = jdbcTemplate.query(findQuery,
                (rs, rowNum) -> {
                    Inventory inv = new Inventory();
                    inv.setProductId(rs.getLong("product_id"));
                    inv.setAvailableQty(rs.getInt("available_qty"));
                    return inv;
                },
                productId
        );

        if (list.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(list.get(0));
    }
    public int deductStock(Long productId, Integer qty) {
        return jdbcTemplate.update(deductQuery, qty, productId, qty);
    }
    public int restoreStock(Long productId, Integer qty) {
        return jdbcTemplate.update(restoreQuery, qty, productId);
    }
}
