package BFS;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class DisplayClass {

	List<ClassInfo> ClassList;
	Scanner in = new Scanner(System.in);

	public DisplayClass(List<ClassInfo> list) {
		this.ClassList = list;
		Display();
	}
	
	

	@SuppressWarnings("unused")
	public void Display() {
		List<String> classesTaken = new ArrayList<>();
		int unitsMin;
		int unitsMax;
		
		

		// ask if user if they have taken class
		for (int i = 0; i < ClassList.size(); i++) {
			takenClass(i);
		}

		System.out.println();
		System.out.println();
		
		// display classes that have been taken
		for (int i = 0; i < ClassList.size(); i++) {
			if (ClassList.get(i).isCompleted()) {
				System.out.println(ClassList.get(i).toString());
				classesTaken.add(ClassList.get(i).getName());
			}

		}
		
		//make static hashmap
		//instead of having List of ClassInfo have list of strings with the class names and then the string of class names will have the key to the array of class info
		HashMap<String, ClassInfo> map = new HashMap<>();
		//store the classinfo in the hashmap
		for(int i = 0; i < ClassList.size(); i++){
			map.put(ClassList.get(i).getName(), ClassList.get(i));
		}
		
		//ask how many units they want to take
		System.out.println("What is the minimum number of Units you would like to take per Semester. "); // put different recemendations, international students, part time full time, 12 is usual, 18 max
		unitsMin = Integer.parseInt(in.next());
		
		System.out.println("What is the maximum number of Units you would like to take per Semester. "); // put different recemendations, international students, part time full time, 12 is usual, 18 max
		unitsMax = Integer.parseInt(in.next());
		
		
		
		//create tree
		MakeTree mt = new MakeTree(classesTaken, map, unitsMin, unitsMax);
			
	}

	//this method goes through each class and asks if the user has taken them
	public void takenClass(int i) {
		String option = "";
		
		System.out.println();
		System.out.println(ClassList.get(i).getName());
		System.out.println("Have you taken this class? (y/n) ");
		option = in.next();

		if (option.equals("y") || option.equals("Y")) {
			ClassList.get(i).setCompleted(true);
			whatGrade(i);

		} else if (option.equals("n") || option.equals("N")) {

		} else {
			System.out.println("Invalid Input");
			takenClass(i);

		}
	}
	
	//this method asks what grade the user received in their taken class
	public void whatGrade(int i){
		String grade = "";
		
		System.out.println("Enter a grade:  ");
		grade = in.next();
		
		if (grade.equals("A+") || grade.equals("A") || grade.equals("A-") || grade.equals("B+")
				|| grade.equals("B") || grade.equals("B-") || grade.equals("C+") || grade.equals("C")
				|| grade.equals("C-") || grade.equals("D+") || grade.equals("D") || grade.equals("D-")
				|| grade.equals("F") ||grade.equals("a+") || grade.equals("a") || grade.equals("a-") 
				|| grade.equals("b+") || grade.equals("b") || grade.equals("b-") || grade.equals("c+")
				|| grade.equals("c") || grade.equals("c-") || grade.equals("d+") || grade.equals("d") 
				|| grade.equals("d-")|| grade.equals("f")) {
			
			ClassList.get(i).setGrade(grade);
			
		}else {
			whatGrade(i);
		}
	}
}
