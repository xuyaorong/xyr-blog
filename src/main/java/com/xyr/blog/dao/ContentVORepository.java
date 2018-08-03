package com.xyr.blog.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xyr.blog.entity.Vo.ContentVo;

public interface ContentVORepository extends JpaRepository<ContentVo, Integer> {

}
