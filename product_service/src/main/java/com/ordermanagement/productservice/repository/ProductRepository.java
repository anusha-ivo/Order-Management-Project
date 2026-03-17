package com.ordermanagement.productservice.repository;

import com.ordermanagement.productservice.config.SqlQueryProvider;
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
    private final SqlQueryProvider sqlQueryProvider;

    public ProductRepository(JdbcTemplate jdbcTemplate,SqlQueryProvider sqlQueryProvider) {
        this.jdbcTemplate = jdbcTemplate;
        this.sqlQueryProvider=sqlQueryProvider;
    }

    public Long create(Product product) {

        KeyHolder keyHolder = new GeneratedKeyHolder();
        String insertQuery = sqlQueryProvider.getQuery("product.insert");

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(insertQuery, new String[]{"product_id"});
            ps.setString(1, product.getStockKeepingUnit());
            ps.setString(2, product.getName());
            ps.setString(3, product.getDescription());
            ps.setBigDecimal(4, product.getPrice());
            ps.setString(5, product.getCurrency());

            return ps;
        }, keyHolder);

        Number key = keyHolder.getKey();
        return key != null ? key.longValue() : null;
    }
    public Optional<Product> findById(Long id) {//optional to value may or may not exist,to avaid null pointer xceptions

        String findByIdQuery = sqlQueryProvider.getQuery("product.findById");
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
                    if (rs.getTimestamp("created_at") != null) {
                        p.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                    }

                    if (rs.getTimestamp("updated_at") != null) {
                        p.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
                    }
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
        String updateQuery = sqlQueryProvider.getQuery("product.update");
        return jdbcTemplate.update(updateQuery,
                product.getStockKeepingUnit(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getCurrency(),
                product.getProductId()
        );
    }
    public int deactivate(Long id)
    {
        String deactivateQuery = sqlQueryProvider.getQuery("product.deactivate");
        return jdbcTemplate.update(deactivateQuery, id);
    }


}
