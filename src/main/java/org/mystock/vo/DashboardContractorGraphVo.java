package org.mystock.vo;

import java.time.LocalDate;

public interface DashboardContractorGraphVo {
	
	LocalDate getChallanDate();
	Integer getIssuedQuantity();
	Integer getReceivedQuantity();
}
