package userapi.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestParam;
import userapi.domain.model.CustomerBalanceHistory;

import java.util.Optional;

public interface CustomerBalanceHistoryRepository extends JpaRepository<CustomerBalanceHistory, Long> {
    // 가장 최신 데이터 가져오기
    Optional<CustomerBalanceHistory> findFirstByCustomer_IdOrderByIdDesc(@RequestParam("customer_id") Long customerId);
}
