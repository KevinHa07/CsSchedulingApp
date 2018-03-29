package BFS;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AvailableClasses {

	private List<String> current;
	private String name;
	private String semester;
	private int year;
	private String y;
	
	//takes in a node that has an arraylist of classInfo 
	public AvailableClasses(List<String> classtaken) {
		this.current = classtaken;
	}
	
	public AvailableClasses(List<String> classtaken, String name, String semester, int year, String y){
		this.current = classtaken;
		this.name = name;
		this.semester = semester;
		this.year = year;
		this.y = y;
	}
	
	//This method takes in the arraylist of ClassInfo to remove classes that the student has already taken from an arraylist of all classes
	public List<String> checkAvailableClasses(List<String> allClasses, Map<String, ClassInfo> allClassInfo, String currSemester, int electiveUnits, boolean constraint) {
		//creates a set of all classes
		Set<String> setOfAvailableClasses = new HashSet<String>(allClasses);
		
		setOfAvailableClasses.removeAll(current);//removes all classes taken from the list of classes to have it only have available classes for student to take
		
		List<String> availableClasses = new ArrayList<>();
		//if student hasn't taken any classes, add classes that has no prerequisites into available class arraylist
		if(current.size() == 0) {
			for(String className: setOfAvailableClasses){
				if(allClassInfo.containsKey(className)) {
					ClassInfo classInfo = allClassInfo.get(className);
					if(classInfo.getSemester().contains(currSemester)) {
						if(classInfo.getPrerequisites() == null){
							availableClasses.add(className);
						}
					}
				}
			}
			
			if(constraint){
				if(!currSemester.equals(semester) || !Integer.toString(year).equals(y)){
					if(availableClasses.contains(name)){
						availableClasses.remove(name);
					}
				}
			}
			return availableClasses;
		}
		//if student has taken classes
		for(String className: setOfAvailableClasses){
			if(allClassInfo.containsKey(className)) {
				ClassInfo classInfo = allClassInfo.get(className);
				if(classInfo.getSemester().contains(currSemester)) {
					if(classInfo.getPrerequisites() == null){
						availableClasses.add(className);
					}
					//if there's a prerequisite, check to see if the student has taken the classes needed to add class to available classes that user can take
					else if(classInfo.getPrerequisites() != null && current.containsAll(classInfo.getPrerequisites()) && electiveUnits < 18) { 
						availableClasses.add(className);	
					}
				}
			}
		}
		if(constraint){
			if(!currSemester.equals(semester) || !Integer.toString(year).equals(y)){
				if(availableClasses.contains(name)){
					availableClasses.remove(name);
				}
			}
		}
		return availableClasses;
	}	
}