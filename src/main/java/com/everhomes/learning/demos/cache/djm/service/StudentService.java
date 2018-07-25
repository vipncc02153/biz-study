package com.everhomes.learning.demos.cache.djm.service;

import com.everhomes.learning.demos.cache.djm.controller.Student;

public interface StudentService {

	Student getStudentById(String id);

	Student updateStudentById(String id);

	void clearCache();
}
