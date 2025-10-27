package org.mystock.entity;


import jakarta.persistence.*;
import lombok.*;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "menu_item")
public class MenuItemEntity {

    @Id
    private String id; // DB primary key

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String type; // e.g., 'item'

    private String classes;
    private String url;
    private String icon;
    private Boolean breadcrumbs;
    private Boolean active;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_group_id")
    private MenuGroupEntity menuGroup;

    public MenuItemEntity(String id, String title, String type, String classes, String url, String icon, Boolean breadcrumbs, MenuGroupEntity menuGroup) {
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