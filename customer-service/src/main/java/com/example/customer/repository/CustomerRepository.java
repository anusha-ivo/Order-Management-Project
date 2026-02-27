package com.example.customer.repository;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class CustomerRepository {
    private final JdbcTemplate jdbcTemplate;
    CustomerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
        public void insertCustomer(UUID customerId,String name,String email,String phone){
            String sql="insert into customers(customer_id,name,email,phone)values(?,?,?,?)";
            jdbcTemplate.update(sql, customerId,name,email,phone);

        }
        public boolean existsByPhone(String phone){
        if(phone==null||phone.isBlank()){
            return false;
        }
        String sql="select count(*) from customers where phone=?";
        Integer count=jdbcTemplate.queryForObject(sql,Integer.class,phone);
        return count!=null && count>0;
        }
    public boolean existsByEmail(String email) {

        String sql = "SELECT COUNT(*) FROM customers WHERE email = ?";

        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);

        return count != null && count > 0;
    }
    public void updateCustomer(UUID customerId,
                               String name,
                               String email,
                               String phone) {

        String sql = "UPDATE customers " +
                "SET name = ?, email = ?, phone = ?, updated_at = CURRENT_TIMESTAMP " +
                "WHERE customer_id = ?";

        jdbcTemplate.update(sql, name, email, phone, customerId);
    }
    public void deleteCustomer(UUID customerId) {
        String sql = "DELETE FROM customers WHERE customer_id = ?";
        jdbcTemplate.update(sql, customerId);
    }
    public boolean existsByEmailForOtherCustomer(String email, UUID customerId) {

        String sql = "SELECT COUNT(*) FROM customers " +
                "WHERE email = ? AND customer_id <> ?";

        Integer count = jdbcTemplate.queryForObject(
                sql,
                Integer.class,
                email,
                customerId
        );

        return count != null && count > 0;
    }
    public boolean existsByPhoneForOtherCustomer(String phone, UUID customerId) {

        if (phone == null || phone.isBlank()) {
            return false;
        }

        String sql = "SELECT COUNT(*) FROM customers " +
                "WHERE phone = ? AND customer_id <> ?";

        Integer count = jdbcTemplate.queryForObject(
                sql,
                Integer.class,
                phone,
                customerId
        );

        return count != null && count > 0;
    }
    }


