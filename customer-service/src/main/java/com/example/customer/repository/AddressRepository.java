package com.example.customer.repository;

import com.example.customer.dto.AddressRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class AddressRepository {
     private JdbcTemplate jdbcTemplate;
     AddressRepository( JdbcTemplate jdbcTemplate){
         this.jdbcTemplate=jdbcTemplate;
     }
    public void insertAddress(UUID addressId,
                              UUID customerId,
                              AddressRequest addressRequest) {

        String sql = "INSERT INTO customer_addresses (" +
                "address_id, customer_id, label, line1, line2, city, state, country, postal_code, is_default" +
                ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(
                sql,
                addressId,
                customerId,
                addressRequest.getLabel(),
                addressRequest.getLine1(),
                addressRequest.getLine2(),
                addressRequest.getCity(),
                addressRequest.getState(),
                addressRequest.getCountry(),
                addressRequest.getPostalCode(),
                addressRequest.getIsDefault()
        );
    }
    public void unsetDefaultAddress(UUID customerId) {
        String sql = "UPDATE customer_addresses " +
                "SET is_default = false " +
                "WHERE customer_id = ? AND is_default = true";

        jdbcTemplate.update(sql, customerId);
    }
    public void updateAddress(UUID addressId,
                              UUID customerId,
                              AddressRequest request) {

        String sql = "UPDATE customer_addresses SET " +
                "label=?, line1=?, line2=?, city=?, state=?, country=?, postal_code=?, is_default=? " +
                "WHERE address_id=? AND customer_id=?";

        jdbcTemplate.update(sql,
                request.getLabel(),
                request.getLine1(),
                request.getLine2(),
                request.getCity(),
                request.getState(),
                request.getCountry(),
                request.getPostalCode(),
                request.getIsDefault(),
                addressId,
                customerId
        );
    }
    public boolean isDefaultAddress(UUID addressId) {
        String sql = "SELECT is_default FROM customer_addresses WHERE address_id = ?";
        Boolean result = jdbcTemplate.queryForObject(sql, Boolean.class, addressId);
        return Boolean.TRUE.equals(result);
    }

    public long countAddresses(UUID customerId) {
        String sql = "SELECT COUNT(*) FROM customer_addresses WHERE customer_id = ?";
        return jdbcTemplate.queryForObject(sql, Long.class, customerId);
    }

    public void deleteAddress(UUID addressId) {
        String sql = "DELETE FROM customer_addresses WHERE address_id = ?";
        jdbcTemplate.update(sql, addressId);
    }

}
