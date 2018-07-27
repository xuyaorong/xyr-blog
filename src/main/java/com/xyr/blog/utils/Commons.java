package com.xyr.blog.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.xyr.blog.constant.WebConst;

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
     * @return
     */
    public static String site_login() {
        return "admin/login";
    }

    /**
     * 返回网站链接下的全址
     *
     * @param sub 后面追加的地址
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
     * @param defalutValue 默认值
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
}
