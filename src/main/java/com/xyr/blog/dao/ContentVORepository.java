package com.xyr.blog.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.xyr.blog.entity.Vo.ContentVo;

public interface ContentVORepository extends JpaRepository<ContentVo, Integer> {

	ContentVo findByCid(int cid);

	List<ContentVo> findBySlug(String slug);

	@Query(value="select * from content_vo t order by cid desc limit ?1",nativeQuery = true)
	List<ContentVo> findByLimit(int limit);
	
	@Query(value="select count(*) from content_vo",nativeQuery = true)
	long findCount();
}
