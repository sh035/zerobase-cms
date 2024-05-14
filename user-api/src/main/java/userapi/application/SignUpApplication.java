package userapi.application;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import userapi.client.MailgunClient;
import userapi.client.mailgun.SendMailForm;
import userapi.domain.SignUpForm;
import userapi.domain.model.Customer;
import userapi.exception.CustomException;
import userapi.exception.ErrorCode;
import userapi.service.SignUpCustomerService;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SignUpApplication {
    private final MailgunClient mailgunClient;
    private final SignUpCustomerService signUpCustomerService;

    public void customerVerify(String email, String code) {
        signUpCustomerService.verifyEmail(email, code);
    }

    public String customerSignUp(SignUpForm form) {
        if (signUpCustomerService.isEmailExist(form.getEmail())) {
            throw new CustomException(ErrorCode.ALREADY_REGISTER_USER);
        } else {
            Customer c = signUpCustomerService.signUp(form);
            LocalDateTime now = LocalDateTime.now();

            String code = getRandomCode();
            SendMailForm sendMailForm = SendMailForm.builder()
                    .from("tester@zerobase.com")
                    .to(form.getEmail())
                    .subject("Verification Email!")
                    .text(getVerificationEmailBody(form.getEmail(), form.getName(), getRandomCode()))
                    .build();

            mailgunClient.sendMail(sendMailForm);
            signUpCustomerService.changeCustomerValidateEmail(c.getId(), code);
            return "회원 가입에 성공하였습니다.";
        }
    }

    private String getRandomCode() {
        return RandomStringUtils.random(10,true, true);
    }
    private String getVerificationEmailBody(String email, String name, String code) {
        StringBuilder builder = new StringBuilder();
        return builder.append("Hello ").append(name)
                .append("! Please Click Link for verification.\n\n")
                .append("http://localhost:8080/signup/verify/customer/verify?email=")
                .append(email)
                .append("&code=")
                .append(code).toString();

    }
}
