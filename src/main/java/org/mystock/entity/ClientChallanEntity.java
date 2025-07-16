package org.mystock.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "clientchallaninfo")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClientChallanEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "challannumber", nullable = false)
	private Integer challanNumber;

	@Column(name = "challandate", nullable = false)
	private LocalDate challanDate;

	@ManyToOne
	@JoinColumn(name = "client_id", nullable = false)
	private ClientEntity client;

	@Column(name = "challantype", nullable = false) // I - Issue, R - Received
	private String challanType;

	@Column(name = "createdon", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
	private LocalDateTime createdOn;

	@OneToMany(mappedBy = "challan", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	private Set<ClientChallanItemEntity> challanItems;
}
