package userapi.application;

import com.zerobase.config.JwtAuthenticationProvider;
import com.zerobase.domain.common.UserType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import userapi.domain.SignInForm;
import userapi.domain.model.Customer;
import userapi.domain.model.Seller;
import userapi.exception.CustomException;
import userapi.exception.ErrorCode;
import userapi.service.CustomerService;
import userapi.service.seller.SellerService;

@Service
@RequiredArgsConstructor
public class SignInApplication {

    private final CustomerService customerService;
    private final SellerService sellerService;
    private final JwtAuthenticationProvider provider;

    public String customerLoginToken(SignInForm form) {
        // 1. 로그인 가능 여부
        Customer c = customerService.findValidCustomer(form.getEmail(), form.getPassword())
                .orElseThrow(() -> new CustomException(ErrorCode.LOGIN_CHECK_FAIL));
        return provider.createToken(c.getEmail(), c.getId(), UserType.CUSTOMER);
    }

    public String sellerLoginToken(SignInForm form) {
        // 1. 로그인 가능 여부
        Seller s = sellerService.findValidSeller(form.getEmail(), form.getPassword())
                .orElseThrow(() -> new CustomException(ErrorCode.LOGIN_CHECK_FAIL));
        return provider.createToken(s.getEmail(), s.getId(), UserType.SELLER);
    }
}
