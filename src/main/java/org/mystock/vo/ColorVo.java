package org.mystock.vo;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
}
