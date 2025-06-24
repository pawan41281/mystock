package org.mystock.vo;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DesignVo {
	private Long id;
	private String design;
	private String description;
	private String color;
	private boolean active;
    private LocalDateTime createdOn;
}
