package org.mystock.service.impl;

import org.mystock.repository.ClientChallanRepository;
import org.mystock.repository.ContractorChallanRepository;
import org.mystock.service.DashboardGraphService;
import org.mystock.vo.DashboardGraphVo;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DashboardGraphServiceImpl implements DashboardGraphService {

	private final ClientChallanRepository clientChallanRepository;
	private final ContractorChallanRepository contractorChallanRepository;

	@Override
	public DashboardGraphVo getDashboardGraphValues() {
		DashboardGraphVo vo = new DashboardGraphVo();
		vo.setDashboardClientGraphVos(clientChallanRepository.getDashboardClientGraphData());
		vo.setDashboardContractorGraphVos(contractorChallanRepository.getDashboardContractorGraphData());
		return vo;
	}

}
