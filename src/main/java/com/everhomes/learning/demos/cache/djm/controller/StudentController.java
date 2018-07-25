package com.everhomes.learning.demos.cache.djm.controller;

import com.everhomes.learning.demos.cache.djm.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/student")
public class StudentController {

	@Autowired
	private StudentService studentService;

	@RequestMapping("/getStudentById")
	public Student getStudentById(String id) {
		Student student = studentService.getStudentById(id);
		return student;
	}

	@RequestMapping("/updateStudentById")
	public Student updateStudentById(String id) {
		Student student = studentService.updateStudentById(id);
		return student;
	}

	@RequestMapping("/clearCache")
	public void clearCache(String id) {
		studentService.clearCache();
	}
}
