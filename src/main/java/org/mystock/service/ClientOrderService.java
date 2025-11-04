package org.mystock.service;

import org.mystock.vo.ClientOrderVo;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface ClientOrderService {

    ClientOrderVo save(ClientOrderVo vo);

    Set<ClientOrderVo> saveAll(Set<ClientOrderVo> vos);

    ClientOrderVo findById(Long id);

    ClientOrderVo deleteById(Long id);

    List<ClientOrderVo> findAll(Integer orderNumber, Long clientId, LocalDate fromOrderDate, LocalDate toOrderDate);

    List<ClientOrderVo> findAll(Integer orderNumber, LocalDate fromOrderDate, LocalDate toOrderDate, Long clientId, Long designId, Long colorId, Long qualityId);
}