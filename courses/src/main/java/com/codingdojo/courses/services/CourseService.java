package com.codingdojo.courses.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.codingdojo.courses.models.Course;
import com.codingdojo.courses.repositories.CourseRepo;

@Service
public class CourseService {
	
	private final CourseRepo courseRepo;
	
	public CourseService(CourseRepo courseRepo) {
		this.courseRepo = courseRepo;
	}

	public List<Course> getAllCourses(){
		return courseRepo.findAll();
	}
	
	public Course getCourseById(Long id) {
		Optional<Course> optCourse = courseRepo.findById(id);
		if(optCourse.isPresent()) {
			return optCourse.get();
		}
		return null;
	}
	
	public void saveCourse(Course course) {
		courseRepo.save(course);
	}
}
