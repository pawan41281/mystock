package org.mystock.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginVo {
	
	private String userId;

	//This allows input but hides it in responses
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;
}