package org.mystock.service.impl;

import lombok.AllArgsConstructor;
import org.mystock.service.ContractorAccountStatementService;
import org.mystock.service.ContractorChallanService;
import org.mystock.service.ContractorPaymentService;
import org.mystock.vo.ContractorAccountStatementVo;
import org.mystock.vo.ContractorChallanVo;
import org.mystock.vo.ContractorPaymentVo;
import org.mystock.vo.ContractorVo;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ContractorAccountStatementServiceImpl implements ContractorAccountStatementService {

    private final ContractorChallanService contractorChallanService;
    private final ContractorPaymentService contractorPaymentService;

//    @Override
//    public List<ContractorAccountStatementVo> findAll(LocalDate fromDate, LocalDate toDate, Long contractorId) {
//        List<ContractorChallanVo> challanList = contractorChallanService.findAll(null, contractorId, fromDate, toDate, "R");
//        List<ContractorPaymentVo> paymentList = contractorPaymentService.findAll(fromDate, toDate, contractorId);
//
//        Map<ContractorVo, List<ContractorChallanVo>> challanMap = challanList.stream().collect(Collectors.groupingBy(ContractorChallanVo::getContractor));
//        Map<ContractorVo, List<ContractorPaymentVo>> paymentMap = paymentList.stream().collect(Collectors.groupingBy(ContractorPaymentVo::getContractor));
//
//        Map<ContractorVo, Double> challanTotals = challanList.stream()
//                .filter(challan -> challan.getChallanItems() != null) // skip nulls safely
//                .collect(Collectors.groupingBy(
//                        ContractorChallanVo::getContractor,
//                        Collectors.summingDouble(challan ->
//                                challan.getChallanItems().stream()
//                                        .filter(Objects::nonNull)
//                                        .mapToDouble(item -> item.getRate() * item.getQuantity())
//                                        .sum()
//                        )
//                ));
//
//        Map<ContractorVo, Integer> contractorPayments = paymentList.stream()
//                .filter(payment -> payment.getContractor() != null)
//                .collect(Collectors.groupingBy(
//                        payment -> payment.getContractor(),
//                        Collectors.summingInt(ContractorPaymentVo::getPaymentAmount)
//                ));
//
//        ContractorAccountStatementVo contractorAccountStatementVo = new ContractorAccountStatementVo();
//
//        return List.of();
//    }


    @Override
    public List<ContractorAccountStatementVo> findAll(LocalDate fromDate, LocalDate toDate, Long contractorId) {

        List<ContractorChallanVo> challanList = contractorChallanService.findAll(null, contractorId, fromDate, toDate, "R");
        List<ContractorPaymentVo> paymentList = contractorPaymentService.findAll(fromDate, toDate, contractorId);

        // --- 1️⃣ Work Done (Rate * Quantity) ---
        Map<ContractorVo, Double> challanTotals = challanList.stream()
                .filter(challan -> challan.getChallanItems() != null)
                .collect(Collectors.groupingBy(
                        ContractorChallanVo::getContractor,
                        Collectors.summingDouble(challan ->
                                challan.getChallanItems().stream()
                                        .filter(Objects::nonNull)
                                        .mapToDouble(item -> item.getRate() * item.getQuantity())
                                        .sum()
                        )
                ));

        // --- 2️⃣ Payments ---
        Map<ContractorVo, Integer> contractorPayments = paymentList.stream()
                .filter(payment -> payment.getContractor() != null)
                .collect(Collectors.groupingBy(
                        ContractorPaymentVo::getContractor,
                        Collectors.summingInt(ContractorPaymentVo::getPaymentAmount)
                ));

        // --- 3️⃣ Merge Both Maps ---
        List<ContractorAccountStatementVo> result = challanTotals.entrySet().stream()
                .map(entry -> {
                    ContractorVo contractor = entry.getKey();
                    Double workDoneAmount = entry.getValue();
                    Integer paymentDoneAmount = contractorPayments.getOrDefault(contractor, 0);

                    ContractorAccountStatementVo vo = new ContractorAccountStatementVo();
                    vo.setContractorId(contractor.getId());
                    vo.setContractorName(contractor.getContractorName());
                    vo.setFromDate(fromDate);
                    vo.setToDate(toDate);
                    vo.setWorkDoneAmount(workDoneAmount.floatValue());
                    vo.setPaymentDoneAmount(paymentDoneAmount.floatValue());
                    return vo;
                })
                .collect(Collectors.toList());

        // --- 4️⃣ Include contractors who have payments but no challans ---
        contractorPayments.keySet().stream()
                .filter(contractor -> !challanTotals.containsKey(contractor))
                .forEach(contractor -> {
                    ContractorAccountStatementVo vo = new ContractorAccountStatementVo();
                    vo.setContractorId(contractor.getId());
                    vo.setContractorName(contractor.getContractorName());
                    vo.setFromDate(fromDate);
                    vo.setToDate(toDate);
                    vo.setWorkDoneAmount(0f);
                    vo.setPaymentDoneAmount(contractorPayments.get(contractor).floatValue());
                    result.add(vo);
                });

        return result;
    }

}