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

	@Column(name = "challan_number", nullable = false)
	private Integer challanNumber;

	@Column(name = "challan_date", nullable = false)
	private LocalDate challanDate;

	@ManyToOne
	@JoinColumn(name = "client_id", nullable = false)
	private ClientEntity client;

	@ManyToOne
	@JoinColumn(name = "order_id")
	private ClientOrderEntity order;

	@Column(name = "challan_type", nullable = false) // I - Issue, R - Received
	private String challanType;

	@Column(name = "created_on", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
	private LocalDateTime createdOn;

	@OneToMany(mappedBy = "challan", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	private Set<ClientChallanItemEntity> challanItems;

	@ManyToOne
	@JoinColumn(name = "created_by", nullable = false)
	private UserEntity user;
}