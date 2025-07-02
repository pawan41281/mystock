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
public class ContractorChalaanVo {

	private Long id;
	private Integer chalaanNumber;
	private Date chalaanDate;
	private ContractorVo contractor;
	private String chalaanType;//I - Issue  R - Received
	private LocalDateTime createdOn;
	private List<ContractorChalaanItemVo> chalaanItems;
}
