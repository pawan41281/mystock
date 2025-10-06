package org.mystock.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "contractor_payment_info")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContractorPaymentEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "paymentdate", nullable = false)
	private LocalDate paymentDate;

	@Column(name = "paymentamount", nullable = false)
	private int paymentAmount;

	@ManyToOne
	@JoinColumn(name = "contractor_id", nullable = false)
	private ContractorEntity contractor;

	@Column(name = "remarks")
	private String remarks;

	@Column(name = "createdon", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
	private LocalDateTime createdOn;

	@ManyToOne
	@JoinColumn(name = "createdby", nullable = false)
	private UserEntity user;
}