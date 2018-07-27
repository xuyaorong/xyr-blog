package com.xyr.blog.utils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

	/**
	 * 设置记住密码cookie
	 *
	 * @param response
	 * @param uid
	 */
	public static void setCookie(HttpServletResponse response, Integer uid) {
		try {
			String val = Tools.enAes(uid.toString(), WebConst.AES_SALT);
			boolean isSSL = false;
			Cookie cookie = new Cookie(WebConst.USER_IN_COOKIE, val);
			cookie.setPath("/");
			cookie.setMaxAge(60 * 30);
			cookie.setSecure(isSSL);
			response.addCookie(cookie);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取cookie中的用户id
	 *
	 * @param request
	 * @return
	 */
	public static Integer getCookieUid(HttpServletRequest request) {
		if (null != request) {
			Cookie cookie = cookieRaw(WebConst.USER_IN_COOKIE, request);
			if (cookie != null && cookie.getValue() != null) {
				try {
					String uid = Tools.deAes(cookie.getValue(), WebConst.AES_SALT);
					return StringUtils.isNotBlank(uid) && Tools.isNumber(uid) ? Integer.valueOf(uid) : null;
				} catch (Exception e) {
				}
			}
		}
		return null;
	}

	/**
	 * 从cookies中获取指定cookie
	 *
	 * @param name
	 *            名称
	 * @param request
	 *            请求
	 * @return cookie
	 */
	private static Cookie cookieRaw(String name, HttpServletRequest request) {
		javax.servlet.http.Cookie[] servletCookies = request.getCookies();
		if (servletCookies == null) {
			return null;
		}
		for (javax.servlet.http.Cookie c : servletCookies) {
			if (c.getName().equals(name)) {
				return c;
			}
		}
		return null;
	}
	
	/**
     * 获取保存文件的位置,jar所在目录的路径
     *
     * @return
     */
    public static String getUplodFilePath() {
        String path = TaleUtils.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        path = path.substring(1, path.length());
        try {
            path = java.net.URLDecoder.decode(path, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        int lastIndex = path.lastIndexOf("/") + 1;
        path = path.substring(0, lastIndex);
        File file = new File("");
        return file.getAbsolutePath() + "/";
    }
}
