package com.ordermanagement.customer.exceptions;

public class CustomerNotFound extends Exception {
    public CustomerNotFound(Long customerId) {
        super("Customer not found with id: " + customerId);
    }
}
