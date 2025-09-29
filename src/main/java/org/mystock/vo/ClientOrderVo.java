package org.mystock.vo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

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
}
