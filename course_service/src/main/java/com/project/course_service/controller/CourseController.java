package com.project.course_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.course_service.model.Course;
import com.project.course_service.service.CourseService;

@RestController
public class CourseController {
	
	@Autowired
	CourseService courseService;
	
	@PostMapping("/registerCourse")
	public void registerCourse(@RequestBody Course course) {
		courseService.registerCourse(course);
	}
	
	@GetMapping("/courses")
	public ResponseEntity<List<Course>> getCourses() {
		
		return new ResponseEntity( courseService.getCourses(), HttpStatus.OK);
	}
	
	@PutMapping("/courses/{id}")
	public void updateCourse(@PathVariable int id,@RequestBody Course course) {
		courseService.updateCourse(id,course);
	}
	
	@DeleteMapping("/courses/{id}")
	public void deleteCourse(@PathVariable int id) {
		courseService.deleteCourse(id);
	}
	
	@GetMapping("/courses/{id}")
	public ResponseEntity<Course> getCourseonId(@PathVariable int id) {
		 return new ResponseEntity(courseService.getCourseOnId(id), HttpStatus.OK);
	}
	
	

}
