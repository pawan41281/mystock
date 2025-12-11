package org.mystock.repository;

import org.mystock.entity.ContractorPaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ContractorPaymentRepository extends JpaRepository<ContractorPaymentEntity, Long> {
	List<ContractorPaymentEntity> findByPaymentDateBetweenOrPaymentAmountBetweenOrContractor_IdOrderByPaymentDateDescContractor_IdAscPaymentAmountDesc(@NonNull LocalDate paymentDateStart, @NonNull LocalDate paymentDateEnd, @Nullable int paymentAmountStart, @Nullable int paymentAmountEnd, @Nullable Long id);
	List<ContractorPaymentEntity> findByPaymentDateBetweenOrContractor_Id(@NonNull LocalDate paymentDateStart, @NonNull LocalDate paymentDateEnd, @Nullable Long id);

	List<ContractorPaymentEntity> findByPaymentDateBetweenAndPaymentAmountBetweenAndContractor_IdOrderByPaymentDateDescContractor_IdAscPaymentAmountDesc(@NonNull LocalDate paymentDateStart, @NonNull LocalDate paymentDateEnd, @Nullable int paymentAmountStart, @Nullable int paymentAmountEnd, @Nullable Long id);
	List<ContractorPaymentEntity> findByPaymentDateBetweenOrPaymentAmountBetweenOrderByPaymentDateDescContractor_IdAscPaymentAmountDesc(@NonNull LocalDate paymentDateStart, @NonNull LocalDate paymentDateEnd, @Nullable int paymentAmountStart, @Nullable int paymentAmountEnd);
	List<ContractorPaymentEntity> findByPaymentDateBetweenAndPaymentAmountBetweenOrderByPaymentDateDescContractor_IdAscPaymentAmountDesc(@NonNull LocalDate paymentDateStart, @NonNull LocalDate paymentDateEnd, @Nullable int paymentAmountStart, @Nullable int paymentAmountEnd);
	List<ContractorPaymentEntity> findByPaymentDateBetweenAndContractor_Id(@NonNull LocalDate paymentDateStart, @NonNull LocalDate paymentDateEnd, @Nullable Long id);

	List<ContractorPaymentEntity> findByPaymentDateBetweenOrderByPaymentDateDescContractor_IdAscPaymentAmountDesc(@NonNull LocalDate paymentDateStart, @NonNull LocalDate paymentDateEnd);
	List<ContractorPaymentEntity> findByPaymentDateBetween(@NonNull LocalDate paymentDateStart, @NonNull LocalDate paymentDateEnd);

}