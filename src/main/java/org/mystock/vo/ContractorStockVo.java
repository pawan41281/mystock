package org.mystock.vo;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ContractorStockVo {

	private Long id;
	private ContractorVo contractor;
	private DesignVo design;
	private ColorVo color;
	private Integer openingBalance=0;
	private Integer balance=0;
	private LocalDateTime updatedOn = LocalDateTime.now();
	private LocalDateTime createdOn = LocalDateTime.now();
}
