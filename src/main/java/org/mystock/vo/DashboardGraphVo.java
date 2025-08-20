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
public class DashboardGraphVo {

	private List<DashboardClientGraphVo> dashboardClientGraphVos;
	private List<DashboardContractorGraphVo> dashboardContractorGraphVos;

}
