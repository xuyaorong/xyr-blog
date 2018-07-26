package com.xyr.blog.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
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

	/**
	 * md5加密
	 *
	 * @param source
	 *            数据源
	 * @return 加密字符串
	 */
	public static String MD5encode(String source) {
		if (StringUtils.isBlank(source)) {
			return null;
		}
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException ignored) {
		}
		byte[] encode = messageDigest.digest(source.getBytes());
		StringBuilder hexString = new StringBuilder();
		for (byte anEncode : encode) {
			String hex = Integer.toHexString(0xff & anEncode);
			if (hex.length() == 1) {
				hexString.append('0');
			}
			hexString.append(hex);
		}
		return hexString.toString();
	}
}
