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

	private UserVo user;
}