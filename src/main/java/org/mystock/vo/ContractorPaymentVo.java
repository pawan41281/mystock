package org.mystock.vo;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ContractorPaymentVo {

	private Long id;
	
	@NotNull
	private LocalDate paymentDate;

	@NotNull
	private int paymentAmount;
	
	@NotNull
	private ContractorVo contractor;

	private String remarks;

	private LocalDateTime createdOn = LocalDateTime.now();

	private UserVo user;
}