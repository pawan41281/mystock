package org.mystock.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleVo {

	//@JsonIgnore
	private Long id;

	private String name;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private UserVo user;

	public RoleVo(Long id, String name){
		this.id=id;
		this.name=name;
	}
}