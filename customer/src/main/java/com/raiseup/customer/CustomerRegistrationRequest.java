package com.raiseup.customer;

public record CustomerRegistrationRequest(
        String firstName,
        String lastName,
        String email) {

}
