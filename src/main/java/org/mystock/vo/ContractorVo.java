package org.mystock.vo;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ContractorVo {

	private Long id;

	@NotNull
	private String contractorName;
	
	public String getContractorName() {
		return contractorName!=null?contractorName.toUpperCase():"";
	}

	private String address;

	private String city;

	private String state;

	private String country;

	private String email;

	private String mobile;

	private String gstNo;

	private Boolean active;

	private LocalDateTime createdOn = LocalDateTime.now();
}
