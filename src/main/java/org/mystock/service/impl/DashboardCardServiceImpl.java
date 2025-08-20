package org.mystock.service.impl;

import org.mystock.repository.ClientChallanRepository;
import org.mystock.repository.ContractorChallanRepository;
import org.mystock.service.DashboardCardService;
import org.mystock.vo.DashboardCardVo;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DashboardCardServiceImpl implements DashboardCardService {

	private final ClientChallanRepository clientChallanRepository;
	private final ContractorChallanRepository contractorChallanRepository;

	@Override
	public DashboardCardVo getDashboardCardValues() {
		DashboardCardVo vo = new DashboardCardVo();
		vo.setDashboardCurrentMonthClientCardVos(clientChallanRepository.getCurrentMonthChallanCount());
		vo.setDashboardCurrentMonthContractorCardVos(contractorChallanRepository.getCurrentMonthChallanCount());
		vo.setDashboardPreviousDayClientCardVos(clientChallanRepository.getPreviousDayChallanCount());
		vo.setDashboardPreviousDayContractorCardVos(contractorChallanRepository.getPreviousDayChallanCount());
		return vo;
	}

}
