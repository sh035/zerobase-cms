package userapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import userapi.domain.SignUpForm;
import userapi.domain.model.Customer;
import userapi.domain.repository.CustomerRepository;

@Service
@RequiredArgsConstructor
public class SignUpCustomerService {

    private final CustomerRepository customerRepository;

    public Customer signUp(SignUpForm form) {
        return customerRepository.save(Customer.from(form));

    }
}
