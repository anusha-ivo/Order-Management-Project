package com.ordermanagement.customer.controller;

import com.ordermanagement.customer.dto.AddressRequest;
import com.ordermanagement.customer.dto.CustomerRequest;
import com.ordermanagement.customer.dto.CustomerResponse;
import com.ordermanagement.customer.exceptions.AddressNotFoundException;
import com.ordermanagement.customer.exceptions.CustomerNotFound;
import com.ordermanagement.customer.exceptions.DuplicateResourceException;
import com.ordermanagement.customer.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService){
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<CustomerResponse> createCustomer(
            @Valid @RequestBody CustomerRequest request) throws DuplicateResourceException {

        CustomerResponse response = customerService.createCustomer(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Long customerId) throws CustomerNotFound {
        customerService.deleteCustomer(customerId);
        return ResponseEntity.ok("Customer deleted successfully");
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<String> updateCustomer(@PathVariable Long customerId,
                                                 @Valid @RequestBody CustomerRequest request) throws CustomerNotFound, DuplicateResourceException {
        customerService.updateCustomer(customerId, request);
        return ResponseEntity.ok("Customer updated successfully");
    }

    @PostMapping("/{customerId}/addresses")
    public ResponseEntity<Map<String, Object>> createAddress(@PathVariable Long customerId,
                                                             @Valid @RequestBody AddressRequest request) throws CustomerNotFound {
        Long addressId = customerService.createAddress(customerId, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of(
                        "addressId", addressId,
                        "message", "Address created successfully"
                ));
    }

    @PutMapping("/{customerId}/address/{addressId}")
    public ResponseEntity<String> updateAddress(@PathVariable Long customerId,
                                                @PathVariable Long addressId,
                                                @Valid @RequestBody AddressRequest request) throws CustomerNotFound, AddressNotFoundException {
        customerService.updateAddress(customerId, addressId, request);
        return ResponseEntity.ok("Address updated successfully");
    }

    @DeleteMapping("/{customerId}/address/{addressId}")
    public ResponseEntity<String> deleteAddress(@PathVariable Long customerId,
                                                @PathVariable Long addressId) throws CustomerNotFound, AddressNotFoundException {
        customerService.deleteAddress(customerId, addressId);
        return ResponseEntity.ok("Address deleted successfully");
    }
    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerResponse> getCustomer(
            @PathVariable Long customerId) throws CustomerNotFound {

        return ResponseEntity.ok(
                customerService.getCustomer(customerId)
        );
    }
}