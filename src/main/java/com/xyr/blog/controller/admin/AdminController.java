package com.xyr.blog.controller.admin;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.xyr.blog.entity.Bo.RestResponseBo;
import com.xyr.blog.entity.Vo.UserVo;
import com.xyr.blog.service.UserService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/admin")
public class AdminController {

	// @Resource
	// private UserService userService;
	//
	// @RequestMapping(value = "getuser", method = RequestMethod.GET)
	// @ApiOperation(value = "按年月查询", notes = "", response = RestResponseBo.class)
	// public Map<String, UserVo> doLogin(@RequestParam(required = true) String
	// username,
	// @RequestParam(required = true) String password) {
	// UserVo userVo = userService.login(username, password);
	// Map<String, UserVo> map = new HashMap<>();
	// map.put("user", userVo);
	// return map;
	// }

	@ApiOperation(value = "登录", notes = "页面")
	@RequestMapping(value = "index", method = RequestMethod.GET)
	public ModelAndView page1() {
		return new ModelAndView("/index.html");
	}

	//
	@ApiOperation(value = "返回map", notes = "map")
	@RequestMapping(value = "map", method = RequestMethod.GET)
	public Map<String, String> map1() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("name", "zhangsan");
		map.put("age", "28");
		return map;
	}

}
