package org.mystock.vo;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ClientOrderVo {

	private Long id;
	
	@NotNull
	@Min(0)
	private Integer orderNumber;
	
	@NotNull
	private LocalDate orderDate;
	
	@NotNull
	private ClientVo client;

	private LocalDateTime createdOn;
	
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	private Set<ClientOrderItemVo> orderItems;

	private UserVo user;
}