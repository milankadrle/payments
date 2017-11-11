package cz.kadrlem.samples.payments.repository;

import cz.kadrlem.samples.payments.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JPA repository for Payment entity.
 */
public interface PaymentRepository extends JpaRepository<Payment, Long>{

}
