package org.mystock.mapper;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.mystock.entity.MenuItemEntity;
import org.mystock.vo.MenuItemVo;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class MenuItemMapper {

    private final ModelMapper modelMapper;

    public MenuItemVo convert(MenuItemEntity menuItem) {
        return modelMapper.map(menuItem, MenuItemVo.class);
    }

    public MenuItemEntity convert(MenuItemVo menuItemVo) {
        return modelMapper.map(menuItemVo, MenuItemEntity.class);
    }

}