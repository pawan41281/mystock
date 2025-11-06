package org.mystock.service;

import org.mystock.vo.ContractorPaymentVo;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface ContractorPaymentService {

    ContractorPaymentVo save(ContractorPaymentVo vo);

    Set<ContractorPaymentVo> saveAll(Set<ContractorPaymentVo> vos);

    ContractorPaymentVo findById(Long id);

    ContractorPaymentVo deleteById(Long id);

    List<ContractorPaymentVo> findAll(@NonNull LocalDate paymentDateStart, @NonNull LocalDate paymentDateEnd,
                                             @Nullable Integer paymentAmountStart, @Nullable Integer paymentAmountEnd,
                                             @Nullable Long id);

    List<ContractorPaymentVo> findAll(@NonNull LocalDate paymentDateStart, @NonNull LocalDate paymentDateEnd,
                                      @Nullable Long id);

}