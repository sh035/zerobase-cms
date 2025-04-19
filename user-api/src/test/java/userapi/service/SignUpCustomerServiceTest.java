package userapi.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import userapi.domain.SignUpForm;
import userapi.domain.model.Customer;
import userapi.service.customer.SignUpCustomerService;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SignUpCustomerServiceTest {

    @Autowired
    private SignUpCustomerService service;

    @Test
    void signUp() {
        SignUpForm form = SignUpForm.builder()
                .name("name")
                .birth(LocalDate.now())
                .email("@abc@gmail.com")
                .password("1")
                .phone("01000000000")
                .build();
        Customer c = service.signUp(form);
        assertNotNull(c.getId());
        assertNotNull(c.getCreatedAt());
    }

}