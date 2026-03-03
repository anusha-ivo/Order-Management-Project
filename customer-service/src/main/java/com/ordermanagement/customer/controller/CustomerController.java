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

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService){
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<CustomerResponse> createCustomer(
            @Valid @RequestBody CustomerRequest request)
            throws DuplicateResourceException, CustomerNotFound {

        CustomerResponse response = customerService.createCustomer(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<CustomerResponse> deleteCustomer(
            @PathVariable Long customerId)
            throws CustomerNotFound {

        CustomerResponse deletedCustomer =
                customerService.deleteCustomer(customerId);

        return ResponseEntity.ok(deletedCustomer);
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<CustomerResponse> updateCustomer(
            @PathVariable Long customerId,
            @Valid @RequestBody CustomerRequest request)
            throws CustomerNotFound, DuplicateResourceException {

        CustomerResponse updatedCustomer =
                customerService.updateCustomer(customerId, request);

        return ResponseEntity.ok(updatedCustomer);
    }

    @PostMapping("/{customerId}/addresses")
    public ResponseEntity<CustomerResponse> createAddress(
            @PathVariable Long customerId,
            @Valid @RequestBody AddressRequest request)
            throws CustomerNotFound {

        CustomerResponse response =
                customerService.createAddress(customerId, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{customerId}/address/{addressId}")
    public ResponseEntity<CustomerResponse> updateAddress(
            @PathVariable Long customerId,
            @PathVariable Long addressId,
            @Valid @RequestBody AddressRequest request)
            throws CustomerNotFound, AddressNotFoundException {

        CustomerResponse response =
                customerService.updateAddress(customerId, addressId, request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{customerId}/address/{addressId}")
    public ResponseEntity<CustomerResponse> deleteAddress(
            @PathVariable Long customerId,
            @PathVariable Long addressId)
            throws CustomerNotFound, AddressNotFoundException {

        CustomerResponse response =
                customerService.deleteAddress(customerId, addressId);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerResponse> getCustomer(
            @PathVariable Long customerId)
            throws CustomerNotFound {

        return ResponseEntity.ok(
                customerService.getCustomer(customerId)
        );
    }
}