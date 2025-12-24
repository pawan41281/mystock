package org.mystock.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class QualityVo {

	private Long id;
	@NotNull
	private String qualityName;
	private Boolean active=true;

	//This allows input but hides it in responses
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private LocalDateTime createdOn;
	
	public String getQualityName() {
		return qualityName!=null?qualityName.toUpperCase():"";
	}

	//This allows input but hides it in responses
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private UserVo user;
}