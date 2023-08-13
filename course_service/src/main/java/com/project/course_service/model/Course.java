package com.project.course_service.model;

import java.util.ArrayList;
import java.util.List;

public class Course {
	private Integer courseId;
	private String courseName;
	private String description;
	
	
	public Course() {
		super();
	}
	public Course(String courseName, String description) {
		super();
		this.courseName = courseName;
		this.description = description;
	}
	private List<Book> books =new ArrayList<Book>();
	
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<Book> getBooks() {
		return books;
	}
	public void setBooks(List<Book> books) {
		this.books = books;
	}
	public Integer getCourseId() {
		return courseId;
	}
	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}
	
	

}
