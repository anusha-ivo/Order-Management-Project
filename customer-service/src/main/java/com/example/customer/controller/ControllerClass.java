package com.example.customer.controller;

import com.example.customer.dto.AddressRequest;
import com.example.customer.dto.CustomerRequest;
import com.example.customer.service.CustomerService;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/customers")
public class ControllerClass {
    private final CustomerService customerService;
    ControllerClass(CustomerService customerService){
        this.customerService=customerService;
    }
    @PostMapping()
    public UUID createCustomer(@RequestBody CustomerRequest request){
        return customerService.createCustomer(request);
    }
    @DeleteMapping("/{customerId}")
    public void deleteCustomer(@PathVariable UUID customerId){
        customerService.deleteCustomer(customerId);
    }
    @PutMapping("/{customerId}")
    public void updateCustomer(@PathVariable UUID customerId,@RequestBody CustomerRequest request){
        customerService.updateCustomer(customerId,request);
    }
    @PostMapping("/{customerId}/addresses")
    public UUID createAddress(@PathVariable UUID customerId,
                              @RequestBody AddressRequest request) {

        return customerService.createAddress(customerId, request);
    }
    @PostMapping("/{customerId}/{addressId}/address")
    public void address(@PathVariable UUID customerId, @PathVariable UUID addressId, @RequestBody AddressRequest request){
        customerService.updateAddress(customerId,addressId,request);
    }
    @DeleteMapping("/{customerId}/addresses/{addressId}")
    public void deleteAddress(@PathVariable UUID customerId, @PathVariable UUID addressId){
        customerService.deleteAddress(customerId,addressId);
    }
}
