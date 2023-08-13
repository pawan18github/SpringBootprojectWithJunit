package com.project.course_service.test;

import static org.hamcrest.CoreMatchers.is;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.project.course_service.model.Course;
import com.project.course_service.repository.CourseRepository;
import com.project.course_service.service.CourseService;

@RunWith(MockitoJUnitRunner.class)
public class CourseServiceTest {
	
	@Mock
	CourseRepository courseRepositoryMock;
	
	@InjectMocks
	CourseService courseService;

	
	@Test
	public void registerCourseMethodWithEmptyCourseName() {
		Course course=new Course();
		course.setCourseName("");
		courseService.registerCourse(course);
		verify(courseRepositoryMock, Mockito.never()).registerCourse(course);
	}
	
	@Test
	public void updateCourseWithCourseNotinDB() {
		Course course=new Course();
		int id=0;
		when(courseRepositoryMock.updateCourse(id,course)).thenReturn(-1);
		assertThat( courseService.updateCourse(0, course),is(-1) );
	}
	
	@Test
	public void deleteCourseWithCourseNotinDB() {
		Course course=new Course();
		int id=0;
		when(courseRepositoryMock.deleteCourse(id)).thenReturn(-1);
		assertThat( courseService.deleteCourse(0),is(-1) );
	}
	

	@Test
	public void getCourseOnIdTest() {
		Course course=new Course("java","java description");
		
		when(courseRepositoryMock.getCourseOnId(1)).thenReturn(course);
		assertEquals("java", courseService.getCourseOnId(1).getCourseName());
	}
	
	@Test
	public void getCoursesTest() {
		when(courseRepositoryMock.getCourses()).thenReturn(null);
		assertEquals(null, courseService.getCourses());
	}
	
	
}
