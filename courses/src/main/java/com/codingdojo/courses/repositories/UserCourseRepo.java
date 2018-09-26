package com.codingdojo.courses.repositories;

import org.springframework.data.repository.CrudRepository;

import com.codingdojo.courses.models.UserCourse;

public interface UserCourseRepo extends CrudRepository<UserCourse, Long>{

}
