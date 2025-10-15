package org.mystock.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleVo {

	@JsonIgnore
	private Long id;

	private String name;

	@JsonIgnore
	private UserVo user;

	public RoleVo(Long id, String name){
		this.id=id;
		this.name=name;
	}
}