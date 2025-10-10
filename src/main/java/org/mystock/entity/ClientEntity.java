package org.mystock.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "client_info")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClientEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "client_name", length = 100, nullable = false)
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

	@Column(name = "gst_no", length = 15)
	private String gstNo;

	@Column(name = "active", columnDefinition = "BOOLEAN DEFAULT TRUE", nullable = false)
	private boolean active;

    @Column(name = "created_on", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    private LocalDateTime createdOn;

	@ManyToOne
	@JoinColumn(name = "created_by", nullable = false)
	private UserEntity user;
}