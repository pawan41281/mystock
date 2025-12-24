package org.mystock.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContractorAccountStatementVo {
	private Long contractorId;
	private String contractorName;
	private LocalDate fromDate;
	private LocalDate toDate;
	private Float workDoneAmount;
	private Float paymentDoneAmount;
}