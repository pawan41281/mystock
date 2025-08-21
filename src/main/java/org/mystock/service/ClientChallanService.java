package org.mystock.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import org.mystock.vo.ClientChallanVo;
import org.mystock.vo.DashboardCurrentMonthClientCardVo;
import org.mystock.vo.DashboardPreviousDayClientCardVo;

public interface ClientChallanService {

	public ClientChallanVo save(ClientChallanVo vo);

	public Set<ClientChallanVo> saveAll(Set<ClientChallanVo> vos);

	public ClientChallanVo findById(Long id);

	public ClientChallanVo deleteById(Long id);

	public List<ClientChallanVo> findAll(Integer challanNumber, Long clientId, LocalDate fromChallanDate,
			LocalDate toChallanDate, String challanType);

	public List<ClientChallanVo> getRecentChallans(String challanType);

	public Integer getCurrentMonthChallanCount(String challanType);

	public List<DashboardCurrentMonthClientCardVo> getCurrentMonthChallanCount();
	
	public List<DashboardPreviousDayClientCardVo> getPreviousDayChallanCount();

}
