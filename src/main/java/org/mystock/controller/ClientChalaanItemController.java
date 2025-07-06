package org.mystock.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/v2/clientchallanitems/")
@Tag(name = "Client Chalaan Item Operations", description = "CRUD Operations for client chalaan item record")
@AllArgsConstructor
public class ClientChalaanItemController{

//	private final ClientChalaanItemService clientChalaanItemService;
}
