package org.mystock.mapper;

import org.modelmapper.ModelMapper;
import org.mystock.entity.ClientChalaanEntity;
import org.mystock.entity.ClientChalaanItemEntity;
import org.mystock.vo.ClientChalaanVo;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ClientChalaanMapper {

	private final ModelMapper modelMapper;
	private final ClientMapper clientMapper;

	public ClientChalaanVo convert(ClientChalaanEntity clientChalaanEntity) {
		ClientChalaanVo clientChalaanVo = modelMapper.map(clientChalaanEntity, ClientChalaanVo.class);
		clientChalaanVo.setClient(clientMapper.convert(clientChalaanEntity.getClient()));
		return clientChalaanVo;
	}

	public ClientChalaanEntity convert(ClientChalaanVo clientChalaanVo) {
		ClientChalaanEntity entity = modelMapper.map(clientChalaanVo, ClientChalaanEntity.class);

		if (entity.getChalaanItems() != null) {
			for (ClientChalaanItemEntity item : entity.getChalaanItems()) {
				// Set parent reference for bi-directional relationship
				item.setChalaan(entity);

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
