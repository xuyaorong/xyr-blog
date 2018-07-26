package com.xyr.blog.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xyr.blog.constant.WebConst;
import com.xyr.blog.entity.Vo.UserVo;


public class TaleUtils {
	private static final Logger LOGGER = LoggerFactory.getLogger(TaleUtils.class);

	private static DataSource newDataSource;

	/**
	 * 返回当前登录用户
	 *
	 * @return
	 */
	public static UserVo getLoginUser(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (null == session) {
			return null;
		}
		return (UserVo) session.getAttribute(WebConst.LOGIN_SESSION_KEY);
	}
}
