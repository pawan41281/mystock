package org.mystock.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
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

	//This allows input but hides it in responses
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;

	private boolean locked;

	private Set<String> roles;

	@Override
	public String toString() {
		return "SignupRequestVo [name=" + name + ", username=" + userId + ", email=" + email + ", mobile=" + mobile
				+ ", locked=" + locked + ", roles=" + roles + "]";
	}

}