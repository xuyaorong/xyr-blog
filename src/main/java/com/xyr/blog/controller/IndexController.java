package com.xyr.blog.controller;

import com.github.pagehelper.PageInfo;
import com.xyr.blog.constant.WebConst;
import com.xyr.blog.entity.Vo.ContentVo;
import com.xyr.blog.service.ContentService;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.util.List;

/**
 * 首页 Created by Administrator on 2017/3/8 008.
 */
@Controller
public class IndexController extends BaseController {
	private static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);

	@Resource
	private ContentService contentService;

	/**
	 * 首页
	 *
	 * @return
	 */
	@GetMapping(value = { "/", "index" })
	public String index(HttpServletRequest request, @RequestParam(value = "limit", defaultValue = "12") int limit) {
		return this.index(request, 1, limit);
	}

	/**
	 * 首页分页
	 *
	 * @param request
	 *            request
	 * @param p
	 *            第几页
	 * @param limit
	 *            每页大小
	 * @return 主页
	 */
	@GetMapping(value = "page/{p}")
	public String index(HttpServletRequest request, @PathVariable Integer p,
			@RequestParam(value = "limit", defaultValue = "12") Integer limit) {
		p = (p < 0 || p > WebConst.MAX_PAGE ? 1 : p) - 1;
		PageInfo<ContentVo> articles = contentService.getContents(p, limit);
		request.setAttribute("articles", articles);
		if (p > 1) {
			this.title(request, "第" + p + "页");
		}
		return this.render("index");
	}

	/**
	 * 文章页
	 *
	 * @param request
	 *            请求
	 * @param cid
	 *            文章主键
	 * @return
	 */
	@GetMapping(value = { "article/{cid}", "article/{cid}.html" })
	public String getArticle(HttpServletRequest request, @PathVariable String cid) {
		ContentVo contents = contentService.getContents(cid);
		if (null == contents || "draft".equals(contents.getStatus())) {
			return this.render_404();
		}
		request.setAttribute("article", contents);
		request.setAttribute("is_post", true);
		completeArticle(request, contents);
		//updateArticleHit(contents.getCid(), contents.getHits());
		return this.render("post");
	}
	
	/**
     * 抽取公共方法
     *
     * @param request
     * @param contents
     */
    private void completeArticle(HttpServletRequest request, ContentVo contents) {
        if (contents.getAllowComment()) {
            String cp = request.getParameter("cp");
            if (StringUtils.isBlank(cp)) {
                cp = "1";
            }
            request.setAttribute("cp", cp);
            PageInfo<Object> commentsPaginator = null;
            request.setAttribute("comments", commentsPaginator);
        }
    }

}
