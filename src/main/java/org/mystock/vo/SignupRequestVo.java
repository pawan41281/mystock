package org.mystock.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequestVo {

	private String name;

	private String userId;

	private String email;

	private String mobile;

	private String password;

	private boolean locked;

	private Set<String> roles;

//	public SignupRequestVo(String name, String userId, String email, String mobile, String password, boolean locked) {
//		super();
//		this.name = name;
//		this.userId = userId;
//		this.email = email;
//		this.mobile = mobile;
//		this.password = password;
//		this.locked = locked;
//	}

//	public SignupRequestVo(String name, String userId, String email, String password) {
//		super();
//		this.name = name;
//		this.userId = userId;
//		this.email = email;
//		this.password = password;
//	}

//	public SignupRequestVo(String userId, String email, String password) {
//		super();
//		this.userId = userId;
//		this.email = email;
//		this.password = password;
//	}

	@Override
	public String toString() {
		return "SignupRequestVo [name=" + name + ", username=" + userId + ", email=" + email + ", mobile=" + mobile
				+ ", locked=" + locked + ", roles=" + roles + "]";
	}

}