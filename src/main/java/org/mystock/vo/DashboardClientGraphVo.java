package org.mystock.vo;

import java.time.LocalDate;

public interface DashboardClientGraphVo {
	
	LocalDate getChallanDate();
	Integer getIssuedQuantity();
	Integer getReceivedQuantity();
}
