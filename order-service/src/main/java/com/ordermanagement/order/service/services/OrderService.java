package com.ordermanagement.order.service.services;

import com.ordermanagement.order.service.exceptions.ResourceNotFoundException;
import com.ordermanagement.order.service.dto.CreateOrderRequest;
import com.ordermanagement.order.service.dto.Order;
import com.ordermanagement.order.service.dto.OrderItem;
import com.ordermanagement.order.service.dto.OrderItemRequest;
import com.ordermanagement.order.service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import tools.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.List;

@Service

public class OrderService {

    private final OrderRepository orderRepository;
    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;
    public OrderService(OrderRepository orderRepository,
                        ObjectMapper objectMapper,
                        RestTemplate restTemplate) {
        this.orderRepository = orderRepository;
        this.objectMapper = objectMapper;
        this.restTemplate = restTemplate;
    }

    @Value("${customer.service}")
    private String customerServiceUrl;

    @Value("${product.service}")
    private String productServiceUrl;

    @Value("${payment.service}")
    private String paymentServiceUrl;
@Transactional
    public Long createOrder(CreateOrderRequest request) throws Exception {
    try{
        String url=customerServiceUrl+"/customers/"+request.getCustomerId();
        restTemplate.getForObject(url, Order.class);
    } catch (Exception e) {
        throw new ResourceNotFoundException("Customer not found with id " + request.getCustomerId());
    }

        BigDecimal totalAmount = BigDecimal.ZERO;
        Order order = new Order();
        order.setCustomerId(request.getCustomerId());
        order.setStatus("CREATED");
        order.setCurrency(request.getCurrency());
        order.setPaymentId(null);
        String shippingJson = objectMapper.writeValueAsString(request.getShippingAddress());
        order.setShippingAddress(shippingJson);
        order.setTotalAmount(BigDecimal.ZERO);
        Long orderId = orderRepository.insertOrder(order);
        for (OrderItemRequest itemRequest : request.getItems()) {
            BigDecimal price = new BigDecimal("1000");
            BigDecimal lineTotal = price.multiply(BigDecimal.valueOf(itemRequest.getQuantity()));
            OrderItem item = new OrderItem();
            item.setOrderId(orderId);
            item.setProductId(itemRequest.getProductId());
            item.setProductNameSnapshot("Sample Product");
            item.setUnitPriceSnapshot(price);
            item.setQuantity(itemRequest.getQuantity());
            item.setLineTotal(lineTotal);
            totalAmount = totalAmount.add(lineTotal);
            orderRepository.updateTotalAmount(orderId, totalAmount);
            orderRepository.saveOrderItem(item);

        }
    return orderId;

    }
    public void confirmOrder(Long orderId){
        Order order=orderRepository.findById(orderId);
        if (order == null) {
            throw new ResourceNotFoundException("Order not found with id " + orderId);
        }
        if (!"CREATED".equals(order.getStatus())) {
            throw new IllegalStateException("Only CREATED orders can be confirmed"); }
        Long paymentId = 100L;
        orderRepository.updatePayment(orderId,paymentId);
        orderRepository.updateStatus(orderId,"CONFIRMED");
    }
    @Transactional
    public void cancelOrder(Long orderId) {

        Order order = orderRepository.findById(orderId);
        if (order == null) {
            throw new ResourceNotFoundException("Order not found with id " + orderId);
        }

        if ("CANCELLED".equals(order.getStatus())) {
            throw new RuntimeException("Order already cancelled");
        }

        if ("CONFIRMED".equals(order.getStatus())) {
            // Refund payment do intigration ..next
            // Restore inventory nxt
        }

        orderRepository.updateStatus(orderId, "CANCELLED");
    }
    public Order getOrder(Long orderId) {

        Order order = orderRepository.findById(orderId);


        if (order == null) {
            throw new ResourceNotFoundException("Order not found with id " + orderId);
        }

        return order;
    }


    public List<OrderItem> getOrderItems(Long orderId) {
        return  orderRepository.findItemsByOrderId(orderId);
    }
}

