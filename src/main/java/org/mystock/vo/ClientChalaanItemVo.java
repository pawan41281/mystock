package org.mystock.vo;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientChalaanItemVo {

	private Long id;
	@JsonIgnore
	private ClientChalaanVo clientChalaan;
	private DesignVo design;
	private String color;
	private Integer quantity;
	private LocalDateTime createdOn;
}
