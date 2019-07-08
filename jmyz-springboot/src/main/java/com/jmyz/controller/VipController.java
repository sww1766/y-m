package com.jmyz.controller;

import com.jmyz.entity.Vip;
import com.jmyz.service.VipService;
import com.jmyz.utils.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

@Api(value = "vip接口管理", tags = "vip接口管理，需要token")
@Log4j2
@RestController
@RequestMapping("vip")
public class VipController {
	@Resource
	private VipService vipService;

//TODO
// 1、根据消费金额，设置消费金额兑换积分的比例，例如10块钱兑换10积分，兑换比例100%，
// 2、根据积分的总额，设置VIP等级，例如5000积分，VIP等级2,10000积分3等等
// 3、设置vip等级的打折率，例如vip等级2，打9折，则1000块钱需付900块
// 4、设置积分抵扣人民币的比例，例如1000积分兑换100人民币，则兑换比率10%
	/**
	 * 根据用户积分总金额设置Vip等级和折扣率
	 */
	@RequiresRoles("admin")
	@ApiOperation(value = "根据用户积分总金额设置Vip等级和折扣率",
			notes = "根据用户积分总金额设置Vip等级和折扣率,必须是管理员操作," +
					"1、vip等级的打折率，例如vip等级1，打95折(95%)，则1000块钱需付950块。" +
					"2、vip等级，例如积分满1000，等级1；积分满5000，等级2等等")
	@PostMapping(value = "operaVip")
	public ResponseData operaVip(@Valid Vip vip) {
		return vipService.operaVip(vip);
	}


	/**
	 * 查询用户的VIP等级
	 */
	@RequiresAuthentication
	@ApiOperation(value = "查询用户的VIP等级",
			notes = "查询用户的VIP等级")
	@PostMapping(value = "queryVip")
	public ResponseData queryVip() {
		return vipService.queryVip();
	}
}
