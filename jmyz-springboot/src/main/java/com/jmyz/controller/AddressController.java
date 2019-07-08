package com.jmyz.controller;

import com.jmyz.entity.Address;
import com.jmyz.service.AddressService;
import com.jmyz.utils.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(value = "收货地址接口", tags = "收货地址接口,需要token")
@Log4j2
@RestController
@RequestMapping("address")
public class AddressController {

	@Resource
	private AddressService addressService;

	/**
	 * 创建收货地址
	 */
	@RequiresAuthentication
	@ApiOperation(value = "创建收货地址", notes = "创建收货地址")
	@PostMapping(value = "createOrUpdate")
	public ResponseData createOrUpdate(Address address) {
		return addressService.createOrUpdate(address);
	}

	/**
	 * 查询我的收货地址列表
	 */
	@RequiresAuthentication
	@ApiOperation(value = "查询我的收货地址,入参createAmountId", notes = "查询我的收货地址")
	@GetMapping(value = "list")
	public ResponseData list(String createAmountId) {
		return addressService.list(createAmountId);
	}

	/**
	 * 查询我的收货地址详情
	 */
	@RequiresAuthentication
	@ApiOperation(value = "查询我的收货地址,入参 addressId", notes = "查询我的收货地址")
	@GetMapping(value = "detail")
	public ResponseData detail(String addressId) {
		return addressService.detail(addressId);
	}
	
}
