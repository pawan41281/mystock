package org.mystock.mapper;

import org.modelmapper.ModelMapper;
import org.mystock.entity.ClientChallanEntity;
import org.mystock.entity.ClientChallanItemEntity;
import org.mystock.vo.ClientChallanVo;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ClientChallanMapper {

	private final ModelMapper modelMapper;
	private final ClientMapper clientMapper;

	public ClientChallanVo toVo(ClientChallanEntity clientChallanEntity) {
		ClientChallanVo clientChallanVo = modelMapper.map(clientChallanEntity, ClientChallanVo.class);
		clientChallanVo.setClient(clientMapper.toVo(clientChallanEntity.getClient()));
		return clientChallanVo;
	}

	public ClientChallanEntity toEntity(ClientChallanVo clientChallanVo) {
		ClientChallanEntity entity = modelMapper.map(clientChallanVo, ClientChallanEntity.class);

		if (entity.getChallanItems() != null) {
			for (ClientChallanItemEntity item : entity.getChallanItems()) {
				// Set parent reference for bi-directional relationship
				item.setChallan(entity);

				// If ID is 0 or present (and you want to create new), null it to avoid detached
				// entity error
				if (item.getId() != null && item.getId() > 0) {
					item.setId(null); // Ensure it's treated as a new item
				}
			}
		}

		return entity;
	}

}
