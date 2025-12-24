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
public class ColorVo {

	private Long id;
	@NotNull
	private String colorName;
	private Boolean active;

	//This allows input but hides it in responses
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private LocalDateTime createdOn;
	
	public String getColorName() {
		return colorName!=null?colorName.toUpperCase():"";
	}

	//This allows input but hides it in responses
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private UserVo user;
}