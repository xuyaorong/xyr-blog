package com.xyr.blog.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.xyr.blog.entity.Vo.UserVo;

@Component
public interface UserVoMapper {
	long selectByUserName(String username);

	List<UserVo> selectByPwd(String pwd);
}
