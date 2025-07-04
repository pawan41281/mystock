package org.mystock.vo;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DesignVo {

	private Long id;

	@NotNull
	private String designName;

	private String description;

	private boolean active = true;

	private LocalDateTime createdOn;
}
