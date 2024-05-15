package userapi.application;

import com.zerobase.config.JwtAuthenticationProvider;
import com.zerobase.domain.common.UserType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import userapi.domain.SignInForm;
import userapi.domain.model.Customer;
import userapi.exception.CustomException;
import userapi.exception.ErrorCode;
import userapi.service.CustomerService;

@Service
@RequiredArgsConstructor
public class SignInApplication {

    private final CustomerService customerService;
    private final JwtAuthenticationProvider provider;

    public String customerLoginToken(SignInForm form) {
        // 1. 로그인 가능 여뷰
        Customer c = customerService.findValidCustomer(form.getEmail(), form.getPassword())
                .orElseThrow(() -> new CustomException(ErrorCode.LOGIN_CHECK_FAIL));
        // 2. 토큰 발행

        // 3. 토큰을 response
        return provider.createToken(c.getEmail(), c.getId(), UserType.CUSTOMER);
    }
}
