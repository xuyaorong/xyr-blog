package com.xyr.blog.controller.admin;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xyr.blog.controller.BaseController;
import com.xyr.blog.entity.Bo.StatisticsBo;
import com.xyr.blog.entity.Vo.ContentVo;
import com.xyr.blog.exception.TipException;
import com.xyr.blog.service.ContentService;
import com.xyr.blog.service.StatisticService;

@Controller("adminIndexController")
@RequestMapping("/admin")
@Transactional(rollbackFor = TipException.class)
public class IndexController extends BaseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);

	@Resource
	private ContentService contentService;
	
	@Resource
	private StatisticService statisticService;
	/**
	 * 页面跳转
	 * 
	 * @return
	 */
	@GetMapping(value = { "", "/index" })
	public String index(HttpServletRequest request) {
		LOGGER.info("Enter admin index method");
		// List<CommentVo> comments = siteService.recentComments(5);
		// List<ContentVo> contents = siteService.recentContents(5);
		// StatisticsBo statistics = siteService.getStatistics();
		// // 取最新的20条日志
		// List<LogVo> logs = logService.getLogs(1, 5);

		// request.setAttribute("comments", comments);
		// request.setAttribute("articles", contents);
		// request.setAttribute("statistics", statistics);
		// request.setAttribute("logs", logs);
		List<ContentVo> contents = contentService.getListByLimit(5);
		StatisticsBo statistics = statisticService.getStatistics();
		request.setAttribute("articles", contents);
		request.setAttribute("statistics", statistics);
		LOGGER.info("Exit admin index method");
		return "admin/index";
	}
}
