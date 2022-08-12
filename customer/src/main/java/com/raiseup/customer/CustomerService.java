package com.raiseup.customer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public record CustomerService(CustomerRepository customerRepository) {
    public void registerCustomer(CustomerRegistrationRequest request) {
        Customer customer= Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .build();

        log.info("Inside CUSTOMERSERVICE {}",customer);


//        TODO://check if email is valid and not taken
//        TODO://Store customer in DB
        customerRepository.save(customer);
    }
}
