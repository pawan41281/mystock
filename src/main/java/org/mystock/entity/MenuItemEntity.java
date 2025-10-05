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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_group_id")
    private MenuGroupEntity menuGroup;

}