package com.xyr.blog.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xyr.blog.entity.Vo.ContentVo;

public interface ContentVORepository extends JpaRepository<ContentVo, Integer> {

	ContentVo findByCid(int cid);
	
	List<ContentVo> findBySlug(String slug);
}
