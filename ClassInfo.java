package BFS;

import java.util.ArrayList;
import java.util.List;

public class ClassInfo {
	
	private String name;
	private int courseNumber;
	private List<String> corequisites;
	private List<String> prerequisites;
	private int units;
	private String grade;
	private boolean completed;
	private boolean isElective;
	private List<String> semester;
	

	//constructor if class has both prerequisite and corequisite
	public ClassInfo(String name, int courseNumber, 
			List<String> corequisites, List<String> prerequisites,
			int units, String grade, boolean completed, boolean elective, List<String> offeredSemesters) {
		this.name = name;
		this.courseNumber = courseNumber;
		this.corequisites = corequisites;
		this.prerequisites = prerequisites;
		this.units = units;
		this.grade = grade;
		this.completed = completed;
		this.isElective = elective;
		this.semester = offeredSemesters;
	}
	
	//constructor if class has just prerequisite
	public ClassInfo(String name, int courseNumber, 
			List<String> prerequisites,
			int units, String grade, boolean completed, boolean elective, List<String> offeredSemesters) {
		this.name = name;
		this.courseNumber = courseNumber;
		this.prerequisites = prerequisites;
		this.units = units;
		this.grade = grade;
		this.completed = completed;
		this.isElective = elective;
		this.semester = offeredSemesters;
	}
	
	//constructor if class has just corequisite
	public ClassInfo(String name, int courseNumber, 
			int units, String grade, 
			List<String> corequisites, boolean completed, boolean elective, List<String> offeredSemesters) {
		this.name = name;
		this.courseNumber = courseNumber;
		this.corequisites = corequisites;
		this.units = units;
		this.grade = grade;
		this.completed = completed;
		this.isElective = elective;
		this.semester = offeredSemesters;
	}
	
	//constructor if class has neither prerequisite and corequisite
	public ClassInfo(String name, int courseNumber,
			int units, String grade, boolean completed, boolean elective, List<String> offeredSemesters) {
		this.name = name;
		this.courseNumber = courseNumber;
		this.units = units;
		this.grade = grade;
		this.completed = completed;
		this.isElective = elective;
		this.semester = offeredSemesters;
	}

	public List<String> getPrerequisites() {
		return prerequisites;
	}

	public void setPrerequisites(List<String> prerequisites) {
		this.prerequisites = prerequisites;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCourseNumber() {
		return courseNumber;
	}

	public void setCourseNumber(int courseNumber) {
		this.courseNumber = courseNumber;
	}

	public List<String> getCorequisites() {
		return corequisites;
	}

	public void setCorequisites(List<String> corequisites) {
		this.corequisites = corequisites;
	}

	public int getUnits() {
		return units;
	}

	public void setUnits(int units) {
		this.units = units;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}
	
	public List<String> getSemester() {
		return semester;
	}

	public void setSemester(List<String> semester) {
		this.semester = semester;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}
	
	public boolean isElective() {
		return isElective;
	}

	public void setElective(boolean isElective) {
		this.isElective = isElective;
	}
	
	private String convertToString(List<String> ci) {
		if(ci != null) {
			String s = "";
			for(String c: ci){
				s += c + ",";
			}
			
			return s;
		}
		else {
			return "";
		}
		
		
	}
	
	public String toString() {
		
		if(this.name == null){
			return "";
		}
		else{
			return "Class: " + this.name + "\nCourse Number: " + this.courseNumber + "\nCorequisites: " + 
					convertToString(this.corequisites) + "\nPrerequisites: " + convertToString(this.prerequisites) + 
					"\nUnits: " + this.units + "\nGrade: " + this.grade + "\nCompleted: " + String.valueOf(this.completed)
					+ "\n";
		}
		
	}
	
}
