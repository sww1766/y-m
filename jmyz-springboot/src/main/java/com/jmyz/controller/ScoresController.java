package com.jmyz.controller;

import com.jmyz.service.ScoresService;
import com.jmyz.utils.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(value = "积分管理接口", tags = "积分管理接口，需要token，且必须是管理员角色账号")
@Log4j2
@RestController
@RequestMapping("score")
public class ScoresController {

	@Resource
	private ScoresService scoresService;


	/**
	 * 查询用户的可用积分和可以兑换的RMB
	 */
	@RequiresAuthentication
	@ApiOperation(value = "查询用户的可用积分",
			notes = "查询用户的可用积分")
	@PostMapping(value = "queryScores")
	public ResponseData queryScores() {
		return scoresService.queryScores();
	}

}
