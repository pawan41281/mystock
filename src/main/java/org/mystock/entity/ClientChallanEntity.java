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
@Table(name = "client_challan_info")
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

	@ManyToOne
	@JoinColumn(name = "order_id")
	private ClientOrderEntity order;

	@Column(name = "challantype", nullable = false) // I - Issue, R - Received
	private String challanType;

	@Column(name = "createdon", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
	private LocalDateTime createdOn;

	@OneToMany(mappedBy = "challan", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	private Set<ClientChallanItemEntity> challanItems;
}