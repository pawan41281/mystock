package org.mystock.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "contractor_challan_info")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContractorChallanEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "challannumber", nullable = false)
	private Integer challanNumber;

	@Column(name = "challandate", nullable = false)
	private LocalDate challanDate;

	@ManyToOne
	@JoinColumn(name = "contractor_id", nullable = false)
	private ContractorEntity contractor;

	@Column(name = "challantype", nullable = false) // I - Issue, R - Received
	private String challanType;

	@Column(name = "createdon", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
	private LocalDateTime createdOn;

	@OneToMany(mappedBy = "challan", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	private Set<ContractorChallanItemEntity> challanItems;

	@ManyToOne
	@JoinColumn(name = "createdby", nullable = false)
	private UserEntity user;
}