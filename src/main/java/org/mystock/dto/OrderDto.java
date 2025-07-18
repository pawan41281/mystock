package org.mystock.dto;

import java.sql.Date;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ordermaster", schema = "mystockdb")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "ordernumber", nullable = false)
	private Integer orderNumber;

	@Column(name = "client", nullable = false)
	private Long client;

	@Column(name = "orderdate", nullable = false)
	private Date orderDate;

	@Column(name = "design", length = 8, nullable = false)
	private String design;

	@Column(name = "color", length = 20, nullable = false)
	private String color;

	@Column(name = "quantity", nullable = false)
	private Integer quantity;

	@Column(name = "remarks", length = 240)
	private String Remarks;

	@Column(name = "active", columnDefinition = "BOOLEAN DEFAULT TRUE", nullable = false)
	private boolean active;

	@Column(name = "createdon", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
	private LocalDateTime createdOn;
}
