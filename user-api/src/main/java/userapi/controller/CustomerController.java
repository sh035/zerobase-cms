package userapi.controller;

import com.zerobase.config.JwtAuthenticationProvider;
import com.zerobase.domain.common.UserVo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import userapi.domain.customer.ChangeBalanceForm;
import userapi.domain.customer.CustomerDto;
import userapi.domain.model.Customer;
import userapi.exception.CustomException;
import userapi.exception.ErrorCode;
import userapi.service.CustomerService;
import userapi.service.customer.CustomerBalanceService;

import static userapi.exception.ErrorCode.NOT_FOUND_USER;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customer")
public class CustomerController {

    private final JwtAuthenticationProvider provider;
    private final CustomerService customerService;
    private final CustomerBalanceService customerBalanceService;

    @GetMapping("/getInfo")
    public ResponseEntity<CustomerDto> getInfo(@RequestHeader(name = "X-AUTH-TOKEN") String token) {
        UserVo vo = provider.getUserVo(token);
        Customer c = customerService.findByIdAndEmail(vo.getId(), vo.getEmail()).orElseThrow(
                () -> new CustomException(NOT_FOUND_USER));

        return ResponseEntity.ok(CustomerDto.from(c));
    }

    @PostMapping("/balance")
    public ResponseEntity<Integer> changeBalance(@RequestHeader(name = "X-AUTH-TOKEN") String token,
                                                 @RequestBody ChangeBalanceForm form) {
        UserVo vo = provider.getUserVo(token);

        return ResponseEntity.ok(customerBalanceService.changeBalance(vo.getId(), form).getCurrentMoney());

    }
}
