package org.mystock.security;

import java.util.Set;

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
public class UserVo {

	private Long id;

	private String name;
	private String userName;
	private String email;
	private String mobile;
	private String password;

	private boolean locked;

	private Set<RoleVo> roles;

	public UserVo(String name, String username, String email, String password) {
		super();
		this.name = name;
		this.userName = username;
		this.email = email;
		this.password = password;
	}

	public UserVo(String name, String username, String email, String mobile, String password) {
		super();
		this.name = name;
		this.userName = username;
		this.email = email;
		this.mobile = mobile;
		this.password = password;
	}

	public UserVo(String name, String username, String email, String password, boolean locked) {
		super();
		this.name = name;
		this.userName = username;
		this.email = email;
		this.password = password;
		this.locked = locked;
	}

	public UserVo(String name, String username, String email, String mobile, String password, boolean locked) {
		super();
		this.name = name;
		this.userName = username;
		this.email = email;
		this.mobile = mobile;
		this.password = password;
		this.locked = locked;
	}

}