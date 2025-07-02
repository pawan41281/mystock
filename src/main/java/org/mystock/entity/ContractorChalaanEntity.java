package org.mystock.entity;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "contractorchalaaninfo")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContractorChalaanEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "chalaannumber", nullable = false)
	private Integer chalaanNumber;

	@Column(name = "chalaandate", nullable = false)
	private Date chalaanDate;

	@ManyToOne
	@JoinColumn(name = "contractor_id", nullable = false)
	private ContractorEntity contractor;

	@Column(name = "chalaantype", nullable = false) // R - Received S - Sent
	private String chalaanType;

	@Column(name = "createdon", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
	private LocalDateTime createdOn;

	@OneToMany(mappedBy = "chalaan", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	private List<ContractorChalaanItemEntity> chalaanItems;
}
