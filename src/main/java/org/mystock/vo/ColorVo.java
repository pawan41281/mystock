package org.mystock.vo;

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
	private LocalDateTime createdOn;
	
	public String getColorName() {
		return colorName!=null?colorName.toUpperCase():"";
	}

	private UserVo user;
}