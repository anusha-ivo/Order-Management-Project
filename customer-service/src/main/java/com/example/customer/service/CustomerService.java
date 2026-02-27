package com.example.customer.service;

import com.example.customer.dto.AddressRequest;
import com.example.customer.dto.CustomerRequest;
import com.example.customer.repository.AddressRepository;
import com.example.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class CustomerService {
    private final AddressRepository addressRepository;
    private final CustomerRepository customerRepository;
    CustomerService(AddressRepository addressRepository,CustomerRepository customerRepository){
        this.customerRepository=customerRepository;
        this.addressRepository=addressRepository;

    }@Transactional
    public UUID createCustomer(CustomerRequest request) {

        if (customerRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        if (customerRepository.existsByPhone(request.getPhone())) {
            throw new RuntimeException("Phone already exists");
        }

        long defaultCount = 0;

        for (AddressRequest a : request.getAddressRequests()) {
            if (Boolean.TRUE.equals(a.getIsDefault())) {
                defaultCount++;
            }
        }

        if (defaultCount != 1) {
            throw new RuntimeException("Exactly one default address required");
        }

        UUID customerId = UUID.randomUUID();

        customerRepository.insertCustomer(
                customerId,
                request.getName(),
                request.getEmail(),
                request.getPhone()
        );

        for (AddressRequest addressRequest : request.getAddressRequests()) {
            addressRepository.insertAddress(
                    UUID.randomUUID(),
                    customerId,
                    addressRequest
            );
        }

        return customerId;
    }

    @Transactional
    public void updateCustomer(UUID customerId, CustomerRequest request) {

        if (customerRepository.existsByEmailForOtherCustomer(
                request.getEmail(), customerId)) {

            throw new RuntimeException("Email already exists");
        }

        if (customerRepository.existsByPhoneForOtherCustomer(
                request.getPhone(), customerId)) {

            throw new RuntimeException("Phone already exists");
        }

        customerRepository.updateCustomer(
                customerId,
                request.getName(),
                request.getEmail(),
                request.getPhone()
        );
    }
    @Transactional
    public void deleteCustomer(UUID customerId) {
        customerRepository.deleteCustomer(customerId);
    }
    @Transactional
    public void addAddress(UUID customerId, AddressRequest request) {

        if (Boolean.TRUE.equals(request.getIsDefault())) {
            addressRepository.unsetDefaultAddress(customerId);
        }

        addressRepository.insertAddress(
                UUID.randomUUID(),
                customerId,
                request
        );
    }
    @Transactional
    public void updateAddress(UUID customerId,
                              UUID addressId,
                              AddressRequest request) {

        if (Boolean.TRUE.equals(request.getIsDefault())) {
            addressRepository.unsetDefaultAddress(customerId);
        }

        addressRepository.updateAddress(addressId, customerId, request);
    }
    @Transactional
    public void deleteAddress(UUID customerId, UUID addressId) {

        if (addressRepository.isDefaultAddress(addressId)) {

            long total = addressRepository.countAddresses(customerId);

            if (total <= 1) {
                throw new RuntimeException("Cannot delete default address without another default");
            }
        }

        addressRepository.deleteAddress(addressId);
    }




    }



