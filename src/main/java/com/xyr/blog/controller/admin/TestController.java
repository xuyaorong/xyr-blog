package com.xyr.blog.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.xyr.blog.constant.WebConst;
import com.xyr.blog.controller.BaseController;
import com.xyr.blog.dao.ContentVORepository;
import com.xyr.blog.entity.Bo.RestResponseBo;
import com.xyr.blog.entity.Vo.ContentVo;
import com.xyr.blog.entity.Vo.UserVo;
import com.xyr.blog.exception.TipException;
import com.xyr.blog.service.ContentService;
import com.xyr.blog.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/test")
@Transactional(rollbackFor = TipException.class)
public class TestController extends BaseController {

	@Autowired
	private ContentVORepository contentVORepository;

	@Resource
	private UserService userService;

	@Resource
	private ContentService contentService;

	@RequestMapping(value = "getuser", method = RequestMethod.GET)
	// @ApiOperation(value = "按年月查询", notes = "", response = RestResponseBo.class)
	public Map<String, UserVo> doLogin(@RequestParam(required = true) String username,
			@RequestParam(required = true) String password) {
		UserVo userVo = userService.login(username, password);
		Map<String, UserVo> map = new HashMap<>();
		map.put("user", userVo);
		return map;
	}

	@RequestMapping(value = "getCon", method = RequestMethod.GET)
	public Map<String, ContentVo> contenVo(@RequestParam(required = true) Integer id) {
		ContentVo contenVo = contentService.findUserById(id);
		Map<String, ContentVo> map = new HashMap<>();
		map.put("contenVo", contenVo);
		return map;
	}

	@RequestMapping(value = "contenVoByPage", method = RequestMethod.GET)
	public PageInfo<ContentVo> index(HttpServletRequest request, @RequestParam int p,
			@RequestParam(value = "limit", defaultValue = "12") int limit) {
		p = p < 0 || p > WebConst.MAX_PAGE ? 1 : p;
		PageInfo<ContentVo> articles = contentService.getContents(p, limit);
		request.setAttribute("articles", articles);
		// if (p > 1) {
		// this.title(request, "第" + p + "页");
		// }
		return articles;
	}

	@RequestMapping(value = "getConten", method = RequestMethod.GET)
	public Page<ContentVo> getConten() {
		int page = 0, size = 2;
		Sort sort = new Sort(Direction.DESC, "cid");
		Pageable pageable = new PageRequest(page, size, sort);
		return contentVORepository.findAll(pageable);

	}
}
