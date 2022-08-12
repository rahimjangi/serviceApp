package com.raiseup.customer;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@AllArgsConstructor
public class CustomerService{
    private final CustomerRepository customerRepository;
    private final RestTemplate restTemplate;

    public void registerCustomer(CustomerRegistrationRequest request) {
        Customer customer= Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .build();

        log.info("Inside CUSTOMERSERVICE {}",customer);
        if(customerRepository.findCustomerByEmail(request.email()).isPresent()){
            FraudCheckResponse fruadCheckResponse = restTemplate.getForObject(
                    "http://FRAUD/api/v1/fraud-check/{customerId}",
                    FraudCheckResponse.class,
                    customerRepository.findCustomerByEmail(request.email()).get().getId()
            );
            if(fruadCheckResponse.isFraudster()){
                throw new IllegalStateException("Fraudster");
            }else{
                throw new IllegalStateException("Email is taken!");
            }
        }else{
            customerRepository.save(customer);
        }
    }
}
