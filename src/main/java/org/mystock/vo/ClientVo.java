package org.mystock.vo;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientVo {
	
	private Long id;
	private String clientname;
	private String address;
	private String city;
	private String state;
	private String country;
	private String email;
	private String mobile;
	private String gstNo;
	private boolean active;
    private LocalDateTime createdOn;
}
