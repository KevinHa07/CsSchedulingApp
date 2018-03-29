package BFS;

import java.util.List;
import java.util.Scanner;

public class Constraint {
	Scanner in = new Scanner(System.in);
	private String name;
	private String semester;
	private String year;
	
	public Constraint() {
		
	}
	
	public Constraint(List<ClassInfo> classInfo, List<String> classesTaken, int maxUnits){
		System.out.println();
		System.out.println();
		System.out.println("Would you like to switch your classes? (yes/no): ");
		String option = in.nextLine();
		if(option.equals("yes")){
			System.out.println();
			System.out.println("What Class would you like to take and when ex(CS1010 Spring 2018): ");
			String[] option1 = in.nextLine().split(" ");
			
			if(isAvailable(option1[0], option1[1], classInfo)){
				
				name = option1[0];
				semester = option1[1];
				year = option1[2];
				
				DisplayClass DC = new DisplayClass(classInfo, classesTaken, true, name, semester, year);
				List<SemesterCourses> sc = DC.Display(maxUnits);
				Constraint c = new Constraint(classInfo, classesTaken, maxUnits);
				
			}else{
				System.out.println("You can not take this class that semester");
				Constraint c = new Constraint(classInfo, classesTaken, maxUnits);
			}
			 
			
		}else{
			System.exit(0);
		}
	}

	public boolean isAvailable(String name, String semester, List<ClassInfo> classInfo){
		
		for(int i = 0; i < classInfo.size(); i++){
			if(classInfo.get(i).getName().equals(name)){
				if(classInfo.get(i).getSemester().contains(semester)){
					return true;
				}
			}
		}
		return false;
	}
}