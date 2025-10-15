package org.mystock.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.Set;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "User details for authentication, authorization, and management.")
public class UserVo {

	private Long id;
	private String name;
	private String userId;
	private String email;
	private String mobile;
	//This allows input but hides it in responses
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;
	private Boolean locked = false;
	private Set<RoleVo> roles;

	public UserVo(String name, String userId, String email, String password) {
		this.name = name;
		this.userId = userId;
		this.email = email;
		this.password = password;
	}

	public UserVo(String name, String userId, String email, String mobile, String password) {
		this.name = name;
		this.userId = userId;
		this.email = email;
		this.mobile = mobile;
		this.password = password;
	}

	public UserVo(String name, String userId, String email, String password, Boolean locked) {
		this.name = name;
		this.userId = userId;
		this.email = email;
		this.password = password;
		this.locked = locked != null ? locked : false;
	}

	public UserVo(String name, String userId, String email, String mobile, String password, Boolean locked) {
		this.name = name;
		this.userId = userId;
		this.email = email;
		this.mobile = mobile;
		this.password = password;
		this.locked = locked != null ? locked : false;
	}

}