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
public class ClientVo {
	
	private Long id;
	
	@NotNull
	private String clientName;
	
	public String getClientName() {
		return clientName!=null?clientName.toUpperCase():"";
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
