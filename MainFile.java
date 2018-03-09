package BFS;

import java.io.IOException;
import java.util.List;

import BFS.FileInput;
import BFS.DisplayClass;
//;/import BFS.Combination;

public class MainFile { 
	
	protected FileInput f;

	@SuppressWarnings("unused")
	public static void main(String[] args) throws IOException {
	
		FileInput f = new FileInput("Sample_Classes.csv");
		
		System.out.println("");
		
		DisplayClass DC = new DisplayClass(f.getListOfClassInfo());
		
		List<SemesterCourses> sc = DC.Display();
	}
}



