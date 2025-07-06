package org.mystock.vo;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContractorChalaanItemVo {

	private Long id;

	@JsonIgnore
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	private ContractorChalaanVo contractorChalaan;

	@NotNull
	private DesignVo design;

	@NotNull
	private ColorVo color;

	@NotNull
	@Min(0)
	private Integer quantity;

	private LocalDateTime createdOn;
}
