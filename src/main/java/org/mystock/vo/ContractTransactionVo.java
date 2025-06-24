package org.mystock.vo;

import java.sql.Date;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContractTransactionVo {
	private Long id;
	private Integer chalaanNumber;
	private Date chalaanDate;
	private Long contractor;
	private String design;
	private String color;
	private Integer quantity;
	private String transactionType;
	private String Remarks;
    private LocalDateTime createdOn;
}
