package org.mystock.vo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
@ToString
public class ClientChalaanVo {

	private Long id;
	
	@NotNull
	@Min(0)
	private Integer chalaanNumber;
	
	@NotNull
	private LocalDate chalaanDate;
	
	@NotNull
	private ClientVo client;

	@NotBlank
	@Pattern(regexp = "I|R")
	private String chalaanType;// I - Issue R - Received
	
	private LocalDateTime createdOn;
	
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	private Set<ClientChalaanItemVo> chalaanItems;
}
