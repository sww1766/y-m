package com.jmyz.controller;

import com.jmyz.entity.Orders;
import com.jmyz.service.OrdersService;
import com.jmyz.utils.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(value = "订单接口", tags = "订单接口,需要token")
@Log4j2
@RestController
@RequestMapping("order")
public class OrderController {

	@Resource
	private OrdersService ordersService;

	/**
	 * 生成订单
	 */
	@RequiresAuthentication
	@ApiOperation(value = "生成订单", notes = "生成订单")
	@PostMapping(value = "create")
	public ResponseData create(Orders orders) {
		return ordersService.create(orders);
	}


	/**
	 * 查询我的订单列表
	 */
	@RequiresAuthentication
	@ApiOperation(value = "查询我的订单列表", notes = "查询我的订单列表")
	@ApiImplicitParams({
			@ApiImplicitParam(
					name = "page",
					value = "当前页码(page)",
					example = "1",
					dataType = "int",
					required = true),
			@ApiImplicitParam(
					name = "pageSize",
					value = "每页展示条数(pageSize)",
					example = "10",
					dataType = "int",
					required = true)
	})
	@GetMapping(value = "list")
	public ResponseData list(Orders orders) {
		return ordersService.list(orders);
	}

	/**
	 * 查询我的订单详情
	 */
	@RequiresAuthentication
	@ApiOperation(value = "查询我的订单详情,入参 id或code", notes = "查询我的订单详情")
	@GetMapping(value = "detail")
	public ResponseData detail(Orders orders) {
		return ordersService.detail(orders);
	}

}
