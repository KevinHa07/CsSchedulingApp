package BFS;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import BFS.FileInput;
import BFS.DisplayClass;

public class MainFile { 

	@SuppressWarnings("unused")
	public static void main(String[] args) throws IOException {
		//classes taken sent in
		List<String> ClassesTaken = new ArrayList<String>();
		
		int maxUnits = 12;
		boolean constraint = false;
		String classInput = "";
		//brings in the csv file and parses it in the FileInput Class
		FileInput f = new FileInput("Sample_Classes.csv");
		
		System.out.println("");
		
		if(!constraint) {
			//sends the list of classes to the DisplayCLass Class
			DisplayClass DC = new DisplayClass(f.getListOfClassInfo(), ClassesTaken, maxUnits);
			
			//returns the list of semester courses
			List<SemesterCourses> sc = DC.Display(maxUnits);
			List<List<SemesterCourses>> listOfPaths = DC.getListOfPaths();
		}
		else {
			//ask if user would like to switch classes
			Constraint c = new Constraint(f.getListOfClassInfo(), ClassesTaken, maxUnits, classInput);
			List<List<SemesterCourses>> listOfPaths = c.getList();
		}
	}
} 