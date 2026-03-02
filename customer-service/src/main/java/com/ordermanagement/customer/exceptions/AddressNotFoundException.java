package com.ordermanagement.customer.exceptions;

public class AddressNotFoundException extends Exception{
    public AddressNotFoundException(Long addressId) {
        super("Address not found with id: " + addressId);
    }
}
