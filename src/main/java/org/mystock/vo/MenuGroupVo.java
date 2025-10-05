package org.mystock.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MenuGroupVo {
    private String id;
    private String title;
    private String type;
    private String icon;
    private List<MenuItemVo> children;
}