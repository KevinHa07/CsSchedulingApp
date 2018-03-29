package BFS;

import java.util.List;

public class SemesterCourses {
	
	String semesterCode;
	List<String> courses;
	List<String> availableCourses;
	
	public SemesterCourses(String sc, List<String> courses, List<String> availableCourses) {
		this.semesterCode = sc;
		this.courses = courses;
		this.availableCourses = availableCourses;
	}

	public String getSemesterCode() {
		return semesterCode;
	}

	public void setSemesterCode(String semesterCode) {
		this.semesterCode = semesterCode;
	}

	public List<String> getCourses() {
		return courses;
	}

	public void setCourses(List<String> courses) {
		this.courses = courses;
	}
	
	public List<String> getAvailableCourses() {
		return availableCourses;
	}

	public void setAvailableCourses(List<String> availableCourses) {
		this.availableCourses = availableCourses;
	}

	public String toString() {
		return semesterCode + courses + "\nAvailable courses: " + availableCourses;
	}
}
