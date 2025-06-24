package org.mystock.vo;

import java.sql.Date;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderVo {
	private Long id;
	private Integer orderNumber;
	private Date orderDate;
	private Long client;
	private String design;
	private String color;
	private Integer quantity;
	private String Remarks;
    private LocalDateTime createdOn;
}
