package org.mystock.service;

import org.mystock.vo.ContractorAccountStatementVo;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.util.List;

public interface ContractorAccountStatementService {

    List<ContractorAccountStatementVo> findAll(
            @NonNull LocalDate fromDate,
            @NonNull LocalDate toDate,
            @Nullable Long contractorId);
}