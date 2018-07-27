package com.xyr.blog.controller.admin;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xyr.blog.controller.BaseController;
import com.xyr.blog.entity.Bo.RestResponseBo;
import com.xyr.blog.entity.Vo.UserVo;
import com.xyr.blog.exception.TipException;
import com.xyr.blog.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/admin")
@Transactional(rollbackFor = TipException.class)
public class TestController {

	@Resource
	private UserService userService;

	@RequestMapping(value = "getuser", method = RequestMethod.GET)
	@ApiOperation(value = "按年月查询", notes = "", response = RestResponseBo.class)
	public Map<String, UserVo> doLogin(@RequestParam(required = true) String username,
			@RequestParam(required = true) String password) {
		UserVo userVo = userService.login(username, password);
		Map<String, UserVo> map = new HashMap<>();
		map.put("user", userVo);
		return map;
	}
}
