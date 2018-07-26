package com.xyr.blog.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.xyr.blog.dao.UserVoMapper;
import com.xyr.blog.entity.Vo.UserVo;
import com.xyr.blog.exception.TipException;
import com.xyr.blog.service.UserService;
import com.xyr.blog.utils.TaleUtils;

@Service
public class UserServiceImpl implements UserService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

	@Resource
	private UserVoMapper userDao;

	@Override
	public Integer insertUser(UserVo userVo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserVo queryUserById(Integer uid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserVo login(String username, String password) {
		// TODO Auto-generated method stub
		if (StringUtils.isBlank(username) && StringUtils.isBlank(password)) {
			throw new TipException("用户名和密码不能为空");
		}
		long count = userDao.selectByUserName(username);
		if (count < 1) {
            throw new TipException("不存在该用户");
        }
        String pwd = TaleUtils.MD5encode(username+password);
        List<UserVo> userVos = userDao.selectByPwd(pwd);
        if (userVos.size()!=1) {
            throw new TipException("用户名或密码错误");
        }
        return userVos.get(0);
	}

	@Override
	public void updateByUid(UserVo userVo) {
		// TODO Auto-generated method stub

	}

}
