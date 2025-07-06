package org.mystock.vo;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StockVo {

	private Long id;
	private DesignVo design;
	private ColorVo color;
	private Integer balance=0;
	private LocalDateTime updatedOn = LocalDateTime.now();
	private LocalDateTime createdOn;
}
