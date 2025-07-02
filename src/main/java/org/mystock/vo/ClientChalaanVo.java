package org.mystock.vo;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientChalaanVo {

	private Long id;
	private Integer chalaanNumber;
	private Date chalaanDate;
	private ClientVo client;
	private String chalaanType;//I - Issue  R - Received
	private LocalDateTime createdOn;
	private List<ClientChalaanItemVo> chalaanItems;
}
