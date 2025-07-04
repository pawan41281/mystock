package org.mystock.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "clientinfo")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClientEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "clentname", length = 100, nullable = false)
	private String clientName;

	@Column(name = "address", length = 240)
	private String address;

	@Column(name = "city", length = 20)
	private String city;

	@Column(name = "state", length = 20)
	private String state;

	@Column(name = "country", length = 20)
	private String country;

	@Column(name = "email", length = 30)
	private String email;

	@Column(name = "mobile", length = 10)
	private String mobile;

	@Column(name = "gstno", length = 10)
	private String gstNo;

	@Column(name = "active", columnDefinition = "BOOLEAN DEFAULT TRUE", nullable = false)
	private boolean active;

    @Column(name = "createdon", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    private LocalDateTime createdOn;
}
