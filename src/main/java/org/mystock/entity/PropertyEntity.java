package org.mystock.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "property_info")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PropertyEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", length = 100, nullable = false, unique = true)
	private String name;

	@Column(name = "value", length = 100, nullable = false, unique = true)
	private String value;

	@Column(name = "created_on", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    private LocalDateTime createdOn;

	@ManyToOne
	@JoinColumn(name = "created_by", nullable = false)
	private UserEntity user;

	public PropertyEntity(Long id, String name, String value, UserEntity userEntity){
		this.id=id;
		this.name=name;
		this.value=value;
		this.user=userEntity;
	}
}