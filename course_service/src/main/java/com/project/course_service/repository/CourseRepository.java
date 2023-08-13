package com.project.course_service.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.project.course_service.model.Book;
import com.project.course_service.model.Course;

@Repository
public class CourseRepository {
	
	@Autowired
	 private JdbcTemplate jdbcTemplate;
	
	private String INSERT_SQL_COURSES = "INSERT INTO COURSEs(course_name,description) values(?,?)";
	private String INSERT_SQL_BOOKS = "INSERT INTO BOOKs(book_name,author,cost,course_id) values(?,?,?,?)";
	private String SELECT_COURSE_SQL = "SELECT DISTINCT COURSE_ID,COURSE_NAME,DESCRIPTION FROM courses";
	private String UPDATE_COURSE_SQL ="UPDATE COURSES SET COURSE_NAME =? , DESCRIPTION=? WHERE COURSE_ID=?";
	private String DELETE_BOOK_SQL = "delete from BOOKS where COURSE_ID = ?";
	private String DELETE_COURSE_SQL = "delete from COURSES where COURSE_ID = ?";
	private String SELECT_COURSE_ON_ID="select course_name,description from courses where course_id=?";
	private String SELECT_BOOK_SQL_ON_ID= "SELECT  BOOK_NAME,AUTHOR,COST FROM BOOKS WHERE COURSE_ID=?";  
			
	public void registerCourse(Course course) {
		KeyHolder holder = new GeneratedKeyHolder();		
		
		
		jdbcTemplate.update(new PreparedStatementCreator() {				
		@Override
		public PreparedStatement createPreparedStatement(Connection connection)	throws SQLException {
			PreparedStatement ps = connection.prepareStatement(INSERT_SQL_COURSES,Statement.RETURN_GENERATED_KEYS);
					ps.setString(1, course.getCourseName());
					ps.setString(2, course.getDescription());
					return ps;
				}
			}, holder);
	
			holder.getKey().intValue();
			course.setCourseId(holder.getKey().intValue());
			
			if(course.getCourseId() !=null) {
				 jdbcTemplate.batchUpdate(INSERT_SQL_BOOKS, new BatchPreparedStatementSetter() {

			            @Override
			            public void setValues(PreparedStatement pStmt, int j) throws SQLException {
			                Book book = course.getBooks().get(j);
			                pStmt.setString(1, book.getBookName());
			                pStmt.setString(2, book.getAuthor());
			                pStmt.setDouble(3, book.getCost());
			                pStmt.setInt(4,course.getCourseId());
			            }

			            @Override
			            public int getBatchSize() {
			                return course.getBooks().size();
			            }

			        });
			}

	}

	public List<Course> getCourses() {		
		

        List<Course> courses = new ArrayList<Course>();

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(SELECT_COURSE_SQL);

        for (Map row : rows) {
        	Course obj = new Course();
            obj.setCourseId((Integer) row.get("COURSE_ID"));
            obj.setCourseName((String) row.get("COURSE_NAME"));
            obj.setDescription((String) row.get("DESCRIPTION"));            
            courses.add(obj);
        }        
        return courses;
	}

	public Integer updateCourse(int id,Course course) {
		
		int updates=jdbcTemplate.update(UPDATE_COURSE_SQL,course.getCourseName(),course.getDescription(),id);
		return updates;
		
	}

	public int  deleteCourse(int id) {
		int booksDeleted=jdbcTemplate.update(DELETE_BOOK_SQL, id);		
		int courseDeleted=jdbcTemplate.update(DELETE_COURSE_SQL, id);
		return courseDeleted;
				
	}

	public Course getCourseOnId(int id) {
		
		Course course=null;
		
		 course=jdbcTemplate.queryForObject(SELECT_COURSE_ON_ID,new Object[] {id},(rs, rowNum) ->
        new Course(
                rs.getString("course_name"),
                rs.getString("description")
        ));
		 
		 if(course.getCourseName()!=null && course.getDescription()!=null) {
			 course.setCourseId(id);
		 }

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(SELECT_BOOK_SQL_ON_ID,id);
        for (Map row : rows) {
        	Book obj = new Book();
            obj.setBookName((String) row.get("BOOK_NAME"));
            obj.setAuthor((String) row.get("AUTHOR"));
            obj.setCost((Float) row.get("COST"));            
            course.getBooks().add(obj);
        }
        return course;
	}
	
	
}
