package BFS;

import java.util.List;

public class SemesterCourses {
	
	String semesterCode;
	List<String> courses;
	
	public SemesterCourses(String sc, List<String> courses) {
		this.semesterCode = sc;
		this.courses = courses;
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
	
	public String toString() {
		return semesterCode + courses;
	}
}
