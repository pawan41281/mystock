package org.mystock.mapper;

import org.modelmapper.ModelMapper;
import org.mystock.entity.ClientOrderEntity;
import org.mystock.entity.ClientOrderItemEntity;
import org.mystock.vo.ClientOrderVo;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ClientOrderMapper {

	private final ModelMapper modelMapper;
	private final ClientMapper clientMapper;

	public ClientOrderVo toVo(ClientOrderEntity clientOrderEntity) {
		ClientOrderVo clientOrderVo = modelMapper.map(clientOrderEntity, ClientOrderVo.class);
		clientOrderVo.setClient(clientMapper.toVo(clientOrderEntity.getClient()));
		return clientOrderVo;
	}

	public ClientOrderEntity toEntity(ClientOrderVo clientOrderVo) {
		ClientOrderEntity entity = modelMapper.map(clientOrderVo, ClientOrderEntity.class);

		if (entity.getOrderItems() != null) {
			for (ClientOrderItemEntity item : entity.getOrderItems()) {
				// Set parent reference for bi-directional relationship
				item.setOrder(entity);

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
