package org.mystock.vo;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContractorVo {
	
	private Long id;
	private String contractorName;
	private String address;
	private String city;
	private String state;
	private String country;
	private String email;
	private String mobile;
	private String gstNo;
	private boolean active=true;
    private LocalDateTime createdOn;
}
