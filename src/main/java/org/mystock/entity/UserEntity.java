package org.mystock.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "userinfo")
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(name = "userId", nullable = false, unique = true)
	private String userId;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false)
	private String mobile;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false, columnDefinition = "boolean default false")
	private boolean locked;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
	private Set<RoleEntity> roles;

	public UserEntity(String name, String userId, String email, String password) {
		super();
		this.name = name;
		this.userId = userId;
		this.email = email;
		this.password = password;
	}

	public UserEntity(String name, String userId, String email, String mobile, String password) {
		super();
		this.name = name;
		this.userId = userId;
		this.email = email;
		this.mobile = mobile;
		this.password = password;
	}

	public UserEntity(String name, String userId, String email, String password, boolean locked) {
		super();
		this.name = name;
		this.userId = userId;
		this.email = email;
		this.password = password;
		this.locked = locked;
	}

	public UserEntity(String name, String userId, String email, String mobile, String password, boolean locked) {
		super();
		this.name = name;
		this.userId = userId;
		this.email = email;
		this.mobile = mobile;
		this.password = password;
		this.locked = locked;
	}

}