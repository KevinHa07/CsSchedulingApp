package BFS;

import java.util.List;
import java.util.Scanner;

public class Constraint {
	Scanner in = new Scanner(System.in);
	private String name;
	private String semester;
	private String year;
	public List<List<SemesterCourses>> listOfPaths;
	
	
	public Constraint() {
		
	}
	
	@SuppressWarnings("unused")
	public Constraint(List<ClassInfo> classInfo, List<String> classesTaken, int maxUnits, String option){
			String[] option1 = option.split(" ");
			
			if(isAvailable(option1[0], option1[1], option1[2], classInfo)){
				
				name = option1[0];
				semester = option1[1];
				year = option1[2];
				
				DisplayClass DC = new DisplayClass(classInfo, classesTaken, true, name, semester, year, maxUnits);
				List<SemesterCourses> sc = DC.Display(maxUnits);
				listOfPaths = DC.getListOfPaths();
			}else{
				System.out.println("You can not take this class that semester");
				
			}
	}

	public boolean isAvailable(String name, String semester, String year, List<ClassInfo> classInfo){
		
		for(int i = 0; i < classInfo.size(); i++){
			if(classInfo.get(i).getName().equals(name)){
				if(classInfo.get(i).getSemester().contains(semester)){
						return true;
				}
			}
		}
		return false;
	}
	
	public List<List<SemesterCourses>> getList(){
		return listOfPaths;
	}
}