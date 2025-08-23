package org.mystock.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "contractorstockinfo", uniqueConstraints = @UniqueConstraint(columnNames = {"contractor_id", "design_id", "color_id"}))
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
	
	@Column(name = "updatedon")
	private LocalDateTime updatedOn = LocalDateTime.now();
	
	@Column(name = "createdon", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
	private LocalDateTime createdOn;
}
