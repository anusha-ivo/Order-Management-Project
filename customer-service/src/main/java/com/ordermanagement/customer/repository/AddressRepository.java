package com.ordermanagement.customer.repository;

import com.ordermanagement.customer.dto.AddressRequest;
import com.ordermanagement.customer.dto.AddressResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AddressRepository {

    private final JdbcTemplate jdbcTemplate;

    public AddressRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Value("${address.insert}")
    private String insertQuery;

    @Value("${address.unset-default}")
    private String unsetDefaultQuery;

    @Value("${address.update}")
    private String updateQuery;

    @Value("${address.is-default}")
    private String isDefaultQuery;

    @Value("${address.count}")
    private String countQuery;

    @Value("${address.delete}")
    private String deleteQuery;

    @Value("${address.exists}")
    private String existsQuery;

    public long insertAddress(long customerId,
                              AddressRequest addressRequest) {

        jdbcTemplate.update(
                insertQuery,
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
        return customerId;
    }

    public void unsetDefaultAddress(long customerId) {
        jdbcTemplate.update(unsetDefaultQuery, customerId);
    }

    public void updateAddress(long addressId,
                              long customerId,
                              AddressRequest request) {

        jdbcTemplate.update(
                updateQuery,
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

    public boolean isDefaultAddress(long addressId) {

        Boolean result = jdbcTemplate.queryForObject(
                isDefaultQuery,
                Boolean.class,
                addressId
        );

        return Boolean.TRUE.equals(result);
    }

    public long countAddresses(long customerId) {

        Long count = jdbcTemplate.queryForObject(
                countQuery,
                Long.class,
                customerId
        );

        return count != null ? count : 0;
    }

    public void deleteAddress(long addressId) {
        jdbcTemplate.update(deleteQuery, addressId);
    }

    public boolean existsByIdAndCustomerId(long addressId, long customerId) {//check address is present or not before dlt,update so we need this method

        Integer count = jdbcTemplate.queryForObject(
                existsQuery,
                Integer.class,
                addressId,
                customerId
        );

        return count != null && count > 0;
    }
    @Value("${address.find-by-customer-id}")
    private String findByCustomerIdQuery;

    public List<AddressResponse> findByCustomerId(long customerId) {

        return jdbcTemplate.query(
                findByCustomerIdQuery,
                (rs, rowNum) -> {
                    AddressResponse a = new AddressResponse();
                    a.setAddressId(rs.getLong("address_id"));
                    a.setLabel(rs.getString("label"));
                    a.setLine1(rs.getString("line1"));
                    a.setLine2(rs.getString("line2"));
                    a.setCity(rs.getString("city"));
                    a.setState(rs.getString("state"));
                    a.setCountry(rs.getString("country"));
                    a.setPostalCode(rs.getString("postal_code"));
                    a.setIsDefault(rs.getBoolean("is_default"));
                    return a;
                },
                customerId
        );
    }
}