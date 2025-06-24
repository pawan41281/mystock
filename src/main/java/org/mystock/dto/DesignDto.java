package org.mystock.dto;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "designmaster", schema = "mystockdb")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DesignDto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "design", length = 8, nullable = false)
	private String design;

	@Column(name = "description", length = 100)
	private String description;
	
	@Column(name = "color", length = 20, nullable = false)
	private String color;
	
	@Column(name = "active", columnDefinition = "BOOLEAN DEFAULT TRUE", nullable = false)
	private boolean active;

    @Column(name = "createdon", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    private LocalDateTime createdOn;
}
