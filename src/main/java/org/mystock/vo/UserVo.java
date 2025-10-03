package org.mystock.vo;

import lombok.*;

import java.util.Set;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserVo {

	private Long id;

	private String name;
	private String userId;
	private String email;
	private String mobile;
	private String password;

	private Boolean locked;

	private Set<RoleVo> roles;

	public UserVo(String name, String userId, String email, String password) {
		super();
		this.name = name;
		this.userId = userId;
		this.email = email;
		this.password = password;
	}

	public UserVo(String name, String userId, String email, String mobile, String password) {
		super();
		this.name = name;
		this.userId = userId;
		this.email = email;
		this.mobile = mobile;
		this.password = password;
	}

	public UserVo(String name, String userId, String email, String password, Boolean locked) {
		super();
		this.name = name;
		this.userId = userId;
		this.email = email;
		this.password = password;
		this.locked = locked;
	}

	public UserVo(String name, String userId, String email, String mobile, String password, Boolean locked) {
		super();
		this.name = name;
		this.userId = userId;
		this.email = email;
		this.mobile = mobile;
		this.password = password;
		this.locked = locked;
	}

}