package com.project.course_service.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.course_service.model.Course;
import com.project.course_service.repository.CourseRepository;

@Service
@Transactional
public class CourseService {
	
	Logger logger = LogManager.getLogger(CourseService.class);
	
	@Autowired
	CourseRepository courseRepository;

	public void registerCourse(Course course) {
		try {
			if(!course.getCourseName().equalsIgnoreCase("")) {
				courseRepository.registerCourse(course);
			}
		}catch (Exception e) {
			logger.error(e.getMessage());
		}
		
	}

	public List<Course> getCourses() {
		List<Course> courses=null;
		try {
		courses= courseRepository.getCourses();
		}catch (Exception e) {
			logger.error(e.getMessage());
		}
		return courses;
		 
	}

	public int updateCourse(int id,Course course) {
		int updates=-1;
		try {
			updates=courseRepository.updateCourse(id,course);		
		}catch (Exception e) {
			logger.error(e.getMessage());
		}
		return updates;
	}

	public int deleteCourse(int id) {
		int deleted=-1;
		try {
		 deleted=courseRepository.deleteCourse(id);
		}catch (Exception e) {
			logger.error(e.getMessage());
		}
		return deleted;
	}

	public Course getCourseOnId(int id) {
		Course course=null;
		try {
			course= courseRepository.getCourseOnId(id);
		}catch (Exception e) {
			logger.error(e.getMessage());
		}
		return course;
		
	}
	
	

}
