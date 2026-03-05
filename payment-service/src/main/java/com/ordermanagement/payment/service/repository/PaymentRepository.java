package com.ordermanagement.payment.service.repository;

import com.ordermanagement.payment.service.entity.Payment;
import com.ordermanagement.payment.service.entity.PaymentStatus;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PaymentRepository {
    private final JdbcTemplate jdbcTemplate;

    @Value("${payment.findById}")
    private String findByIdSql;

    @Value("${payment.findByIdempotencyKey}")
    private String findByIdempotencyKeySql;

    @Value("${payment.findByOrderId}")
    private String findByOrderIdSql;

    @Value("${payment.insert}")
    private String insertSql;

    @Value("${payment.updateStatus}")
    private String updateStatusSql;
    private Payment mapPaymentRow(ResultSet rs, int rowNum) throws SQLException {

        Payment payment = new Payment();

        payment.setPaymentId(rs.getLong("payment_id"));
        payment.setOrderId(rs.getLong("order_id"));
        payment.setAmount(rs.getBigDecimal("amount"));
        payment.setCurrency(rs.getString("currency"));
        payment.setMethod(rs.getString("method"));


        payment.setStatus(
                PaymentStatus.valueOf(rs.getString("status"))
        );

        payment.setIdempotencyKey(rs.getString("idempotency_key"));


        payment.setCreatedAt(
                rs.getTimestamp("created_at").toInstant()
        );

        payment.setUpdatedAt(
                rs.getTimestamp("updated_at").toInstant()
        );

        return payment;
    }
    public Payment findById(Long id) {
        List<Payment> list =
                jdbcTemplate.query(findByIdSql, this::mapPaymentRow, id);

        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
    public Payment findByIdempotencyKey(String key) {

        List<Payment> list =
                jdbcTemplate.query(findByIdempotencyKeySql, this::mapPaymentRow, key);

        if (list.isEmpty()) {
            return null;
        }

        return list.get(0);
    }
    public Long save(Payment payment) {
        return jdbcTemplate.queryForObject(
                insertSql,
                Long.class,
                payment.getOrderId(),
                payment.getAmount(),
                payment.getCurrency(),
                payment.getMethod(),
                payment.getStatus().name(),
                payment.getIdempotencyKey(),
                java.sql.Timestamp.from(payment.getCreatedAt()),
                java.sql.Timestamp.from(payment.getUpdatedAt())
        );
    }
    public void updateStatus(Long id, PaymentStatus status) {
        jdbcTemplate.update(updateStatusSql, status.name(), id);
    }

    public Payment findByOrderId(Long orderId) {

        List<Payment> list =
                jdbcTemplate.query(findByOrderIdSql, this::mapPaymentRow, orderId);

        return list.isEmpty() ? null : list.get(0);
    }
}






