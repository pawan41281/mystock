package org.mystock.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ContractorVo {

	private Long id;

	@NotNull
	private String contractorName;

	private String address;

	private String city;

	private String state;

	private String country;

	private String email;

	private String mobile;

	private String gstNo;

	private Boolean active;

	//This allows input but hides it in responses
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private LocalDateTime createdOn = LocalDateTime.now();

	//This allows input but hides it in responses
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private UserVo user;

	public String getContractorName() {
		return contractorName!=null?contractorName.toUpperCase():"";
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		ContractorVo that = (ContractorVo) o;
		//return Objects.equals(getId(), that.getId()) && Objects.equals(getContractorName(), that.getContractorName()) && Objects.equals(getEmail(), that.getEmail()) && Objects.equals(getMobile(), that.getMobile()) && Objects.equals(getGstNo(), that.getGstNo());
		return Objects.equals(getId(), that.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId(), getContractorName(), getEmail(), getMobile(), getGstNo());
	}
}