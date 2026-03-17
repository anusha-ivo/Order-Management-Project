package com.ordermanagement.productservice.repository;

import com.ordermanagement.productservice.config.SqlQueryProvider;
import com.ordermanagement.productservice.dto.Inventory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class InventoryRepository {
    private final JdbcTemplate jdbcTemplate;
    private final SqlQueryProvider sqlQueryProvider;

    public InventoryRepository(JdbcTemplate jdbcTemplate,SqlQueryProvider sqlQueryProvider) {
        this.jdbcTemplate = jdbcTemplate;
        this.sqlQueryProvider=sqlQueryProvider;
    }

    public void createInitialStock(Long productId, Integer qty) {
        String insertQuery = sqlQueryProvider.getQuery("inventory.insert");

        jdbcTemplate.update(insertQuery, productId, qty);
    }
    public Optional<Inventory> findByProductId(Long productId) {

        String findQuery = sqlQueryProvider.getQuery("inventory.findById");

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

        String deductQuery = sqlQueryProvider.getQuery("inventory.deduct");
        return jdbcTemplate.update(deductQuery, qty, productId, qty);
    }
    public int restoreStock(Long productId, Integer qty) {
        String restoreQuery = sqlQueryProvider.getQuery("inventory.restore");
        return jdbcTemplate.update(restoreQuery, qty, productId);
    }
}
