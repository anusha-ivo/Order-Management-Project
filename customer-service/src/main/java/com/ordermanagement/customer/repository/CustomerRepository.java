package com.ordermanagement.customer.repository;

import com.ordermanagement.customer.dto.CustomerResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;


@Repository
public class CustomerRepository {

    private final JdbcTemplate jdbcTemplate;

    public CustomerRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Value("${customer.insert}")
    private String insertQuery;

    @Value("${customer.exists-email}")
    private String existsEmailQuery;

    @Value("${customer.exists-phone}")
    private String existsPhoneQuery;

    @Value("${customer.update}")
    private String updateQuery;

    @Value("${customer.delete}")
    private String deleteQuery;

    @Value("${customer.exists-email-other}")
    private String existsEmailOtherQuery;

    @Value("${customer.exists-phone-other}")
    private String existsPhoneOtherQuery;

    @Value("${customer.exists-id}")
    private String existsIdQuery;




    public long insertCustomer(String name, String email, String phone) {

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(
                con -> {
                    PreparedStatement ps =
                            con.prepareStatement(insertQuery, new String[]{"customer_id"});
                    ps.setString(1, name);
                    ps.setString(2, email);
                    ps.setString(3, phone);
                    return ps;
                },
                keyHolder
        );

        return keyHolder.getKey().longValue();
    }

    public boolean existsByPhone(String phone) {
        if (phone == null || phone.isBlank()) {
            return false;
        }
        Integer count = jdbcTemplate.queryForObject(existsPhoneQuery, Integer.class, phone);
        return count != null && count > 0;
    }

    public boolean existsByEmail(String email) {
        Integer count = jdbcTemplate.queryForObject(existsEmailQuery, Integer.class, email);
        return count != null && count > 0;
    }

    public void updateCustomer(long customerId, String name, String email, String phone) {
        jdbcTemplate.update(updateQuery, name, email, phone, customerId);
    }

    public void deleteCustomer(long customerId) {
        jdbcTemplate.update(deleteQuery, customerId);
    }

    public boolean existsByEmailForOtherCustomer(String email, long customerId) {
        Integer count = jdbcTemplate.queryForObject(existsEmailOtherQuery, Integer.class, email, customerId);
        return count != null && count > 0;
    }

    public boolean existsByPhoneForOtherCustomer(String phone, long customerId) {
        if (phone == null || phone.isBlank()) {
            return false;
        }
        Integer count = jdbcTemplate.queryForObject(existsPhoneOtherQuery, Integer.class, phone, customerId);
        return count != null && count > 0;
    }
    public boolean existsById(long customerId) {
        Integer count = jdbcTemplate.queryForObject(
                existsIdQuery,
                Integer.class,
                customerId
        );
        return count != null && count > 0;
    }
    @Value("${customer.find-by-id}")
    private String findByIdQuery;

    public CustomerResponse findById(long customerId) {

        return jdbcTemplate.queryForObject(
                findByIdQuery,
                (rs, rowNum) -> {

                    CustomerResponse c = new CustomerResponse();

                    c.setCustomerId(rs.getLong("customer_id"));
                    c.setName(rs.getString("name"));
                    c.setEmail(rs.getString("email"));
                    c.setPhone(rs.getString("phone"));

                    // SAFE timestamp handling
                    if (rs.getTimestamp("created_at") != null) {
                        c.setCreatedAt(
                                rs.getTimestamp("created_at").toLocalDateTime()
                        );
                    }

                    if (rs.getTimestamp("updated_at") != null) {
                        c.setUpdatedAt(
                                rs.getTimestamp("updated_at").toLocalDateTime()
                        );
                    }

                    return c;
                },
                customerId
        );
    }

}