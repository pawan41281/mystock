package org.mystock.controller;

import org.mystock.service.ContractorChalaanItemService;
import org.mystock.vo.ContractorChalaanItemVo;
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
	
	private ContractorChalaanItemService contractorChalaanItemService;
	
	@GetMapping("/{id}")
	public ContractorChalaanItemVo getContractorChalaanItem(@PathVariable Long id) {
		return null;
	}

}
