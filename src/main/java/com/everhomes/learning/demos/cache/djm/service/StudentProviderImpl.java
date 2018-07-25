package com.everhomes.learning.demos.cache.djm.service;

import com.everhomes.learning.demos.cache.djm.controller.Student;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class StudentProviderImpl implements StudentProvider {
	private static final Logger LOGGER = LoggerFactory.getLogger(StudentProviderImpl.class);

	@GetCacheable
	@Override
	public Student getStudentById(String id) {
		LOGGER.info("进行查询的" + id + "没有使用缓存,模拟查询数据库");
		Student student = new Student();
		student.setId("000" + id);
		student.setName("我是代号是：000" + id);
		student.setScore(80);
		return student;
	}

	@UpdateCacheEvict
	@Override
	public Student updateStudentById(String id) {
		LOGGER.info("对" + id + "进行数据更新！");
		Student student = new Student();
		student.setId("000" + id);
		student.setName("我是代号是：000" + id);
		student.setScore(90);
		return student;
	}

	@ClearCache
	@Override
	public void clearCache() {
		LOGGER.info("数据清空了！");
	}
}
