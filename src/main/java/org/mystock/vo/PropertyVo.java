package org.mystock.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PropertyVo {

	private Long id;
	@NotNull
	private String name;
	@NotNull
	private String value;

	//This allows input but hides it in responses
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private LocalDateTime createdOn;

	//This allows input but hides it in responses
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private UserVo user;
}