package com.everhomes.learning.demos.cache.djm.service;

import com.everhomes.learning.demos.cache.djm.controller.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StudentServiceImpl implements StudentService {

	@Autowired
	private StudentProvider studentProvider;

	@Override
	public Student getStudentById(String id) {
		Student student = studentProvider.getStudentById(id);
		return student;
	}

	@Override
	public Student updateStudentById(String id) {
		Student student = studentProvider.updateStudentById(id);
		return student;
	}

	@Override
	public void clearCache() {
		studentProvider.clearCache();
	}
}
