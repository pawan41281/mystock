package org.mystock.vo;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ClientChallanVo {

	private Long id;
	
	@NotNull
	@Min(0)
	private Integer challanNumber;
	
	@NotNull
	private LocalDate challanDate;
	
	@NotNull
	private ClientVo client;
	
	private ClientOrderVo order;

	@NotBlank
	@Pattern(regexp = "I|R")
	private String challanType;// I - Issue R - Received
	
	private LocalDateTime createdOn;
	
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	private Set<ClientChallanItemVo> challanItems;

	private UserVo user;
}