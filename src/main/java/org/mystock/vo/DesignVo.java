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
public class DesignVo {

	private Long id;

	@NotNull
	private String designName;

	public String getDesignName() {
		return designName!=null?designName.toUpperCase():"";
	}
	
	private String description;

	private Boolean active;

	private LocalDateTime createdOn = LocalDateTime.now();
}
