package org.mystock.vo;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequestVo {

	private String name;

	private String username;

	private String email;

	private String mobile;

	private String password;

	private boolean locked;

	private Set<String> roles;

	public SignupRequestVo(String name, String username, String email, String mobile, String password, boolean locked) {
		super();
		this.name = name;
		this.username = username;
		this.email = email;
		this.mobile = mobile;
		this.password = password;
		this.locked = locked;
	}

	public SignupRequestVo(String name, String username, String email, String password) {
		super();
		this.name = name;
		this.username = username;
		this.email = email;
		this.password = password;
	}

	public SignupRequestVo(String username, String email, String password) {
		super();
		this.username = username;
		this.email = email;
		this.password = password;
	}

	@Override
	public String toString() {
		return "SignupRequestVo [name=" + name + ", username=" + username + ", email=" + email + ", mobile=" + mobile
				+ ", locked=" + locked + ", roles=" + roles + "]";
	}

}