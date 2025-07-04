package org.mystock.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "clientchalaaniteminfo")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClientChalaanItemEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "chalaan_id", nullable = false)
	@JsonIgnore
	private ClientChalaanEntity chalaan;

	@ManyToOne
	@JoinColumn(name = "design_id", nullable = false)
	private DesignEntity design;

	@Column(name = "color", length = 20, nullable = false)
	private String color;

	@Column(name = "quantity", nullable = false)
	private Integer quantity;

	@Column(name = "createdon", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
	private LocalDateTime createdOn;
}
