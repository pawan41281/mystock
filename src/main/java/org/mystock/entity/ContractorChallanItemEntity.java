package org.mystock.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "contractor_challan_item_info")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContractorChallanItemEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "challan_id", nullable = false)
	@JsonIgnore
	private ContractorChallanEntity challan;

	@ManyToOne
	@JoinColumn(name = "design_id", nullable = false)
	private DesignEntity design;

	@ManyToOne
	@JoinColumn(name = "color_id", nullable = false)
	private ColorEntity color;

	@Column(name = "quantity", nullable = false)
	private Integer quantity;

	@Column(name = "created_on", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
	private LocalDateTime createdOn;
}