package org.mystock.service;

import org.mystock.vo.ClientChallanVo;
import org.mystock.vo.DashboardCurrentMonthClientCardVo;
import org.mystock.vo.DashboardPreviousDayClientCardVo;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface ClientChallanService {

	ClientChallanVo save(ClientChallanVo vo);

	Set<ClientChallanVo> saveAll(Set<ClientChallanVo> vos);

	ClientChallanVo findById(Long id);

	ClientChallanVo deleteById(Long id);

	List<ClientChallanVo> findAll(Integer challanNumber, Long clientId, Long orderId, LocalDate fromChallanDate,
			LocalDate toChallanDate, String challanType);

	public List<ClientChallanVo> findAll(Integer challanNumber, Long clientId, Integer orderNumber, LocalDate fromChallanDate,
										 LocalDate toChallanDate, String challanType);

	List<ClientChallanVo> getRecentChallans(String challanType);

	Integer getCurrentMonthChallanCount(String challanType);

	List<DashboardCurrentMonthClientCardVo> getCurrentMonthChallanCount();

	List<DashboardPreviousDayClientCardVo> getPreviousDayChallanCount();

}