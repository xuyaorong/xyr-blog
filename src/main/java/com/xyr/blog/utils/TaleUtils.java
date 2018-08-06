package com.xyr.blog.utils;

import org.apache.commons.lang3.StringUtils;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.awt.*;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.Normalizer;
import java.util.Date;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.xyr.blog.constant.WebConst;
import com.xyr.blog.entity.Vo.UserVo;

public class TaleUtils {
	private static final Logger LOGGER = LoggerFactory.getLogger(TaleUtils.class);

	private static DataSource newDataSource;

	/**
     * markdown解析器
     */
    private static Parser parser = Parser.builder().build();
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
    
    /**
     * markdown转换为html
     *
     * @param markdown
     * @return
     */
    public static String mdToHtml(String markdown) {
        if (StringUtils.isBlank(markdown)) {
            return "";
        }
        Node document = parser.parse(markdown);
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        String content = renderer.render(document);
        content = Commons.emoji(content);

        // TODO 支持网易云音乐输出
//        if (TaleConst.BCONF.getBoolean("app.support_163_music", true) && content.contains("[mp3:")) {
//            content = content.replaceAll("\\[mp3:(\\d+)\\]", "<iframe frameborder=\"no\" border=\"0\" marginwidth=\"0\" marginheight=\"0\" width=350 height=106 src=\"//music.163.com/outchain/player?type=2&id=$1&auto=0&height=88\"></iframe>");
//        }
        // 支持gist代码输出
//        if (TaleConst.BCONF.getBoolean("app.support_gist", true) && content.contains("https://gist.github.com/")) {
//            content = content.replaceAll("&lt;script src=\"https://gist.github.com/(\\w+)/(\\w+)\\.js\">&lt;/script>", "<script src=\"https://gist.github.com/$1/$2\\.js\"></script>");
//        }
        return content;
    }
}
