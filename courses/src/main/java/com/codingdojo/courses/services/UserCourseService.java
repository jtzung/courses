package com.codingdojo.courses.services;

import org.springframework.stereotype.Service;

import com.codingdojo.courses.models.UserCourse;
import com.codingdojo.courses.repositories.UserCourseRepo;

@Service
public class UserCourseService {
	
	private final UserCourseRepo userCourseRepo;
	
	public UserCourseService(UserCourseRepo userCourseRepo) {
		this.userCourseRepo = userCourseRepo;
	}
	
	public void saveUC(UserCourse uc) {
		userCourseRepo.save(uc);
	}
}
