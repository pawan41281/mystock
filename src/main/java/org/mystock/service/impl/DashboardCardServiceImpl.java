package org.mystock.service.impl;

import org.mystock.service.ClientChallanService;
import org.mystock.service.ContractorChallanService;
import org.mystock.service.DashboardCardService;
import org.mystock.vo.DashboardCardVo;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DashboardCardServiceImpl implements DashboardCardService {

	private final ContractorChallanService contractorChallanService;
	private final ClientChallanService clientChallanService;
	@Override
	public DashboardCardVo getDashboardCardValues() {
		DashboardCardVo vo = new DashboardCardVo();
		
		vo.setDashboardCurrentMonthClientCardVos(clientChallanService.getCurrentMonthChallanCount());
		vo.setDashboardPreviousDayClientCardVos(clientChallanService.getPreviousDayChallanCount());
		
		
		vo.setDashboardCurrentMonthContractorCardVos(contractorChallanService.getCurrentMonthChallanCount());
		vo.setDashboardPreviousDayContractorCardVos(contractorChallanService.getPreviousDayChallanCount());
		
		return vo;
	}

}
