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
    @JsonIgnore
    private MenuGroupVo menuGroup;
}