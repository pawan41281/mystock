package org.mystock.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class MenuItemVo {
    private String id;
    private String title;
    private String type;
    private String classes;
    private String url;
    private String icon;
    private Boolean breadcrumbs;
    private Boolean active=true;
    @JsonIgnore
    private MenuGroupVo menuGroup;

    public MenuItemVo(String id, String title, String type, String classes, String url, String icon, Boolean breadcrumbs, MenuGroupVo menuGroup) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.classes = classes;
        this.url = url;
        this.icon = icon;
        this.breadcrumbs = breadcrumbs;
        this.active = true;
        this.menuGroup = menuGroup;
    }
}