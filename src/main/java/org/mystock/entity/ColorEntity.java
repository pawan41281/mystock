package org.mystock.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "color_info")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ColorEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "color_name", length = 30, nullable = false, unique = true)
	private String colorName;

	@Column(name = "active", columnDefinition = "BOOLEAN DEFAULT TRUE", nullable = false)
	private boolean active;
	
	@Column(name = "created_on", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    private LocalDateTime createdOn;

	@ManyToOne
	@JoinColumn(name = "created_by", nullable = false)
	private UserEntity user;
}