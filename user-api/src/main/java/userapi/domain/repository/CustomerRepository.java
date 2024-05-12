package userapi.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import userapi.domain.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
