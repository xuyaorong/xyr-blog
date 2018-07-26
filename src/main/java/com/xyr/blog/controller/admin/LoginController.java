package com.xyr.blog.controller.admin;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import com.xyr.blog.controller.BaseController;
import com.xyr.blog.entity.Bo.RestResponseBo;
import com.xyr.blog.exception.TipException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/admin")
@Api(tags="登录控制")
@Transactional(rollbackFor = TipException.class)
public class LoginController extends BaseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

	@ApiOperation(value="获得登录页面", notes="获取图书列表")
	@GetMapping(value = "/login")
	public String login() {
		return "admin/login";
	}
	
	@PostMapping(value = "login")
    @ResponseBody
    public RestResponseBo doLogin(@RequestParam String username,
                                  @RequestParam String password,
                                  @RequestParam(required = false) String remeber_me,
                                  HttpServletRequest request,
                                  HttpServletResponse response) {
		return RestResponseBo.ok();
    }

    /**
     * 注销
     * @param session
     * @param response
     */
    @RequestMapping("/logout")
    public void logout(HttpSession session, HttpServletResponse response, HttpServletRequest request) {
        
    }
}
