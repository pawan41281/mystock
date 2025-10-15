package org.mystock.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class StockVo {

	private Long id;
	private DesignVo design;
	private ColorVo color;
	private Integer openingBalance=0;
	private Integer balance=0;
	//This allows input but hides it in responses
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private LocalDateTime updatedOn = LocalDateTime.now();
	//This allows input but hides it in responses
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private LocalDateTime createdOn = LocalDateTime.now();
}