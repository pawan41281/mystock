package org.mystock.vo;

import java.sql.Date;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderTransactionVo {
	private Long id;
	private Integer orderNumber;
	private Integer chalaanNumber;
	private Date chalaanDate;
	private Long client;
	private String design;
	private String color;
	private Integer quantity;
	private String Remarks;
    private LocalDateTime createdOn;
}
