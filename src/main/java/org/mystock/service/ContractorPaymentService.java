package org.mystock.service;

import org.mystock.vo.ContractorPaymentVo;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface ContractorPaymentService {

    public ContractorPaymentVo save(ContractorPaymentVo vo);

    public Set<ContractorPaymentVo> saveAll(Set<ContractorPaymentVo> vos);

    public ContractorPaymentVo findById(Long id);

    public ContractorPaymentVo deleteById(Long id);

    public List<ContractorPaymentVo> findAll(@NonNull LocalDate paymentDateStart, @NonNull LocalDate paymentDateEnd,
                                             @Nullable Integer paymentAmountStart, @Nullable Integer paymentAmountEnd,
                                             @Nullable Long id);

}