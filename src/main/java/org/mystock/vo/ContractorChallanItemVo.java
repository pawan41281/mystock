package org.mystock.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ContractorChallanItemVo {

	private Long id;

	@JsonIgnore
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	private ContractorChallanVo contractorChallan;

	@NotNull
	private DesignVo design;

	@NotNull
	private ColorVo color;

	@NotNull
	@Min(0)
	private Integer quantity;

	//This allows input but hides it in responses
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private LocalDateTime createdOn = LocalDateTime.now();
}