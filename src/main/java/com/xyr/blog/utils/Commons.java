package com.xyr.blog.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.xyr.blog.constant.WebConst;
import com.xyr.blog.entity.Vo.ContentVo;

@Component
public class Commons {

	public static String THEME = "themes/default";

	private static final List EMPTY = new ArrayList(0);

	/**
	 * 返回gravatar头像地址
	 *
	 * @param email
	 * @return
	 */
	public static String gravatar(String email) {
		String avatarUrl = "https://secure.gravatar.com/avatar";
		if (StringUtils.isBlank(email)) {
			return avatarUrl;
		}
		String hash = TaleUtils.MD5encode(email.trim().toLowerCase());
		return avatarUrl + "/" + hash;
	}

	/**
	 * 获取随机数
	 *
	 * @param max
	 * @param str
	 * @return
	 */
	public static String random(int max, String str) {
		return UUID.random(1, max) + str;
	}

	/**
	 * 网站链接
	 *
	 * @return
	 */
	public static String site_url() {
		return site_url("");
	}

	public static String site_index() {
		return "index";
	}

	/**
	 * 在管理员页面退出登录返回到登录界面
	 * 
	 * @return
	 */
	public static String site_login() {
		return "admin/login";
	}

	/**
	 * 返回网站链接下的全址
	 *
	 * @param sub
	 *            后面追加的地址
	 * @return
	 */
	public static String site_url(String sub) {
		return site_option("site_url") + sub;
	}

	/**
	 * 网站配置项
	 *
	 * @param key
	 * @return
	 */
	public static String site_option(String key) {
		return site_option(key, "");
	}

	/**
	 * 网站配置项
	 *
	 * @param key
	 * @param defalutValue
	 *            默认值
	 * @return
	 */
	public static String site_option(String key, String defalutValue) {
		if (StringUtils.isBlank(key)) {
			return "";
		}
		String str = WebConst.initConfig.get(key);
		if (StringUtils.isNotBlank(str)) {
			return str;
		} else {
			return defalutValue;
		}
	}

	private static final String[] ICONS = { "bg-ico-book", "bg-ico-game", "bg-ico-note", "bg-ico-chat", "bg-ico-code",
			"bg-ico-image", "bg-ico-web", "bg-ico-link", "bg-ico-design", "bg-ico-lock" };

	/**
	 * 显示文章图标
	 *
	 * @param cid
	 * @return
	 */
	public static String show_icon(int cid) {
		return ICONS[cid % ICONS.length];
	}

	/**
	 * 返回文章链接地址
	 *
	 * @param contents
	 * @return
	 */
	public static String permalink(ContentVo contents) {
		return permalink(contents.getCid(), contents.getSlug());
	}

	/**
	 * 返回文章链接地址
	 *
	 * @param cid
	 * @param slug
	 * @return
	 */
	public static String permalink(Integer cid, String slug) {
		return site_url("/article/" + (StringUtils.isNotBlank(slug) ? slug : cid.toString()));
	}

	/**
	 * 显示文章缩略图，顺序为：文章第一张图 -> 随机获取
	 *
	 * @return
	 */
	public static String show_thumb(ContentVo contents) {
		int cid = contents.getCid();
		int size = cid % 20;
		size = size == 0 ? 1 : size;
		return "/user/img/rand/" + size + ".jpg";
	}

	/**
	 * 获取社交的链接地址
	 *
	 * @return
	 */
	public static Map<String, String> social() {
		final String prefix = "social_";
		Map<String, String> map = new HashMap<>();
		map.put("weibo", WebConst.initConfig.get(prefix + "weibo"));
		map.put("zhihu", WebConst.initConfig.get(prefix + "zhihu"));
		map.put("github", WebConst.initConfig.get(prefix + "github"));
		map.put("twitter", WebConst.initConfig.get(prefix + "twitter"));
		return map;
	}

	/**
	 * 网站标题
	 *
	 * @return
	 */
	public static String site_title() {
		return site_option("site_title");
	}

	public static String show_categories(String categories) throws UnsupportedEncodingException {
		if (StringUtils.isNotBlank(categories)) {
			String[] arr = categories.split(",");
			StringBuffer sbuf = new StringBuffer();
			for (String c : arr) {
				sbuf.append("<a href=\"/category/" + URLEncoder.encode(c, "UTF-8") + "\">" + c + "</a>");
			}
			return sbuf.toString();
		}
		return show_categories("默认分类");
	}
}
