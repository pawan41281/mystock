package org.mystock.vo;

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

	@Schema(description = "Unique database ID of the user", example = "1")
	private Long id;

	@Schema(description = "Full name of the user", example = "John Doe")
	private String name;

	@Schema(description = "Unique user identifier used for login or internal mapping", example = "USR001")
	private String userId;

	@Schema(description = "Email address of the user", example = "john.doe@example.com")
	private String email;

	@Schema(description = "Mobile number of the user", example = "9876543210")
	private String mobile;

	@Schema(
			description = "Password for the user (masked in responses). Used only during creation or updates.",
			example = "Test@123",
			accessMode = Schema.AccessMode.WRITE_ONLY
	)
	private String password;

	@Schema(description = "Indicates whether the user account is locked", example = "false")
	private Boolean locked = false;

	@Schema(description = "Roles assigned to the user")
	private Set<RoleVo> roles;

	public String getPassword() {
		// Hide actual password when serializing
		return "********";
	}

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