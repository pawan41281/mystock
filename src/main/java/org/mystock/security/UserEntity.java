package org.mystock.security;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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

	@Column(name = "userName", nullable = false, unique = true)
	private String userName;

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

	public UserEntity(String name, String userName, String email, String password) {
		super();
		this.name = name;
		this.userName = userName;
		this.email = email;
		this.password = password;
	}

	public UserEntity(String name, String userName, String email, String mobile, String password) {
		super();
		this.name = name;
		this.userName = userName;
		this.email = email;
		this.mobile = mobile;
		this.password = password;
	}

	public UserEntity(String name, String userName, String email, String password, boolean locked) {
		super();
		this.name = name;
		this.userName = userName;
		this.email = email;
		this.password = password;
		this.locked = locked;
	}

	public UserEntity(String name, String userName, String email, String mobile, String password, boolean locked) {
		super();
		this.name = name;
		this.userName = userName;
		this.email = email;
		this.mobile = mobile;
		this.password = password;
		this.locked = locked;
	}

}