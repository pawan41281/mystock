package org.mystock.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "contractor_stock_info", uniqueConstraints = @UniqueConstraint(columnNames = {"contractor_id", "design_id", "color_id"}))
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContractorStockEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "contractor_id", nullable = false)
	private ContractorEntity contractor;

	@ManyToOne
	@JoinColumn(name = "design_id", nullable = false)
	private DesignEntity design;

	@ManyToOne
	@JoinColumn(name = "color_id", nullable = false)
	private ColorEntity color;

	@Column(name = "balance", nullable = false)
	private Integer balance = 0;

	@Column(name = "obalance", nullable = false)
	private Integer openingBalance = 0;
	
	@Column(name = "updated_on")
	private LocalDateTime updatedOn = LocalDateTime.now();
	
	@Column(name = "created_on", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
	private LocalDateTime createdOn;
}