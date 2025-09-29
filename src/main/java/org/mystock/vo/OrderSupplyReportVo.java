package org.mystock.vo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderSupplyReportVo {

	private Integer orderNumber;
	private List<OrderVo> orderVoList;
	private List<OrderTransactionVo> orderTransactionVoList;

}
