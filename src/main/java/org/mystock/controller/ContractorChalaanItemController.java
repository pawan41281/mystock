package org.mystock.controller;

import java.util.HashMap;
import java.util.Map;

import org.mystock.apiresponse.ApiResponseVo;
import org.mystock.apiresponse.ApiResponseVoWrapper;
import org.mystock.service.ContractorChalaanItemService;
import org.mystock.vo.ContractorChalaanItemVo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/v2/contractorchallanitems/")
@Tag(name = "Contractor Chalaan Item Operations", description = "CRUD Operations for contractor chalaan item record")
@AllArgsConstructor
public class ContractorChalaanItemController {

	private final ContractorChalaanItemService contractorChalaanItemService;

//	@GetMapping("/{id}")
//	public ResponseEntity<ApiResponseVo<ContractorChalaanItemVo>> getContractorChalaanItem(@PathVariable Long id) {
//		ContractorChalaanItemVo contractorChalaanItemVo = contractorChalaanItemService.getById(id);
//		Map<String, String> metadata = new HashMap<>();
//		metadata.put("recordcount", contractorChalaanItemVo != null ? "1" : "0");
//		return ResponseEntity
//				.ok(ApiResponseVoWrapper.success(contractorChalaanItemVo != null ? "Record found" : "Record not found",
//						contractorChalaanItemVo, metadata));
//	}

}
