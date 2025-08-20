package org.mystock.vo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DashboardCardVo {

	private List<DashboardCurrentMonthContractorCardVo> dashboardCurrentMonthContractorCardVos;
	private List<DashboardCurrentMonthClientCardVo> dashboardCurrentMonthClientCardVos;
	private List<DashboardPreviousDayContractorCardVo> dashboardPreviousDayContractorCardVos;
	private List<DashboardPreviousDayClientCardVo> dashboardPreviousDayClientCardVos;

}
