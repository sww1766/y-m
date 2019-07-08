package com.jmyz.controller;

import com.jmyz.entity.ScoreRate;
import com.jmyz.service.ScoreRateService;
import com.jmyz.utils.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

@Api(value = "积分和RMB兑换比率接口", tags = "积分和RMB兑换比率接口，需要token")
@Log4j2
@RestController
@RequestMapping("rate")
public class ScoreRateController {
	@Resource
	private ScoreRateService scoreRateService;


	/**
	 * 设置消费金额和积分的兑换比率，设置积分兑换人民币的比率
	 */
	@RequiresRoles("admin")
	@ApiOperation(value = "设置消费金额和积分的兑换比率，设置积分兑换人民币的比率",
			notes = "设置消费金额和积分的兑换比率，设置积分兑换人民币的比率,必须是管理员操作," +
					"1、金额兑换积分的比例,例如10块钱兑换10积分，兑换比例100%。" +
					"2、积分兑换金额的比率，例如1000积分兑换100人民币，则兑换比率10%。")
	@PostMapping(value = "operaRate")
	public ResponseData operaRate(@Valid ScoreRate scoreRate) {
		return scoreRateService.operaRate(scoreRate);
	}

}
