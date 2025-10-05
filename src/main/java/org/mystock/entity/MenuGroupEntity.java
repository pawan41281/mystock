package org.mystock.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "menu_group")
public class MenuGroupEntity {

    @Id
    private String id; // DB primary key

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String type; // e.g., 'group'

    private String icon;

    @OneToMany(mappedBy = "menuGroup", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<MenuItemEntity> children;

    public MenuGroupEntity(String id, String title, String type, String icon){
        this.id=id;
        this.title=title;
        this.type=type;
        this.icon=icon;
    }
}