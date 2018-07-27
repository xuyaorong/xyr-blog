package com.xyr.blog.controller.admin;

import java.io.IOException;

import javax.annotation.Resource;
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

import com.xyr.blog.constant.WebConst;
import com.xyr.blog.controller.BaseController;
import com.xyr.blog.entity.Bo.RestResponseBo;
import com.xyr.blog.entity.Vo.UserVo;
import com.xyr.blog.exception.TipException;
import com.xyr.blog.service.UserService;
import com.xyr.blog.utils.TaleUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/admin")
@Transactional(rollbackFor = TipException.class)
public class LoginController extends BaseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

	@ApiOperation(value = "获得登录页面", notes = "获取图书列表")
	@GetMapping(value = "/login")
	public String login() {
		return "admin/login";
	}

	@Resource
	private UserService usersService;

	@PostMapping(value = "login")
	@ResponseBody
	public RestResponseBo doLogin(@RequestParam String username, @RequestParam String password,
			@RequestParam(required = false) String remeber_me, HttpServletRequest request,
			HttpServletResponse response) {
		Integer error_count = cache.get("login_error_count");
		try {
			UserVo user = usersService.login(username, password);
			request.getSession().setAttribute(WebConst.LOGIN_SESSION_KEY, user);
			if (StringUtils.isNotBlank(remeber_me)) {
				TaleUtils.setCookie(response, user.getUid());
			}
//			logService.insertLog(LogActions.LOGIN.getAction(), null, request.getRemoteAddr(), user.getUid());
		} catch (Exception e) {
			error_count = null == error_count ? 1 : error_count + 1;
			if (error_count > 3) {
				return RestResponseBo.fail("您输入密码已经错误超过3次，请10分钟后尝试");
			}
			cache.set("login_error_count", error_count, 10 * 60);
			String msg = "登录失败";
			if (e instanceof TipException) {
				msg = e.getMessage();
			} else {
				LOGGER.error(msg, e);
			}
			return RestResponseBo.fail(msg);
		}
		return RestResponseBo.ok();
	}

	/**
	 * 注销
	 * 
	 * @param session
	 * @param response
	 */
	@RequestMapping("/logout")
	public void logout(HttpSession session, HttpServletResponse response, HttpServletRequest request) {

	}
}
