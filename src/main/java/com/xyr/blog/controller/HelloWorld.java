package com.xyr.blog.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class HelloWorld {
	@RequestMapping("/hello")
	public String hello(HttpServletRequest request,
			@RequestParam(value = "name", required = false, defaultValue = "springboot-thymeleaf") String name) {
		request.setAttribute("name", name);
		return "/admin/hello";
	}
}
