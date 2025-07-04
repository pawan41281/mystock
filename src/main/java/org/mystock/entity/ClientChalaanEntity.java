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
@Table(name = "clientchalaaninfo")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClientChalaanEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "chalaannumber", nullable = false)
	private Integer chalaanNumber;

	@Column(name = "chalaandate", nullable = false)
	private LocalDate chalaanDate;

	@ManyToOne
	@JoinColumn(name = "client_id", nullable = false)
	private ClientEntity client;

	@Column(name = "chalaantype", nullable = false) // I - Issue, R - Received
	private String chalaanType;

	@Column(name = "createdon", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
	private LocalDateTime createdOn;

	@OneToMany(mappedBy = "chalaan", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	private Set<ClientChalaanItemEntity> chalaanItems;
}
