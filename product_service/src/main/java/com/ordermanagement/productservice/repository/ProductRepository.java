package com.ordermanagement.productservice.repository;

import com.ordermanagement.productservice.dto.Product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Repository
public class ProductRepository {
    private final JdbcTemplate jdbcTemplate;

    public ProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Value("${product.insert}")
    public String insertQuery;
    @Value("${product.findById}")
    private String findByIdQuery;
    @Value("${product.update}")
    private String updateQuery;
    @Value("${product.deactivate}")
    private String deactivateQuery;
    public Long create(Product product) {

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(insertQuery, new String[]{"product_id"});

            ps.setString(1, product.getStockKeepingUnit());
            ps.setString(2, product.getName());
            ps.setString(3, product.getDescription());
            ps.setBigDecimal(4, product.getPrice());
            ps.setString(5, product.getCurrency());

            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }
    public Optional<Product> findById(Long id) {

        List<Product> list = jdbcTemplate.query(findByIdQuery,
                (rs, rowNum) -> {
                    Product p = new Product();
                    p.setProductId(rs.getLong("product_id"));
                    p.setStockKeepingUnit(rs.getString("stock_keeping_unit"));
                    p.setName(rs.getString("name"));
                    p.setDescription(rs.getString("description"));
                    p.setPrice(rs.getBigDecimal("price"));
                    p.setCurrency(rs.getString("currency"));
                    p.setStatus(rs.getString("status"));
                    return p;
                },
                id
        );

        if (list.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(list.get(0));
    }
    public int update(Product product) {
        return jdbcTemplate.update(updateQuery,
                product.getStockKeepingUnit(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getCurrency(),
                product.getProductId()
        );
    }
    public int deactivate(Long id) {
        return jdbcTemplate.update(deactivateQuery, id);
    }

}
