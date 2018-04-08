package BFS;

import java.time.Year;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class Node{
    private List<String> classTaken;
    
    private Node parent;
    private int numOfElectiveUnits = 0;
    private String semesterCode;
    private boolean isGoal = false;
    private List<String> availableClasses;
    
    List<Node> children = new ArrayList<>();
    List<SemesterCourses> semesterCourses = new ArrayList<>();

	private List<Node> path = new ArrayList<>();
    private List<String> takenClassesFromPath = new ArrayList<String>();

    public Node(List<String> data) {
        this.classTaken = data;
    }

    public List<Node> addChild(Node child) {
    	
        child.setParent(this);
        children.add(child);
        return children;
    }

	public void addChildren(List<Node> children) {
        for(Node t : children) {
            t.setParent(this);
        }
        this.children.addAll(children);
    }

	public List<Node> getChildren(HashMap<String, ClassInfo> listOfClasses, List<String> classesTaken, int unitsMax, String semester, int year, boolean constraint) {
		
		Set<String> keySet = listOfClasses.keySet();
		List<String> allClasses = new ArrayList<String>(keySet);
		
		//find classes that are available to take next
			AvailableClasses av = new AvailableClasses(classesTaken);
			List<String> available = av.checkAvailableClasses(allClasses, listOfClasses, semester, this.numOfElectiveUnits, constraint);
			this.availableClasses = available;
			//find all combination
			Combinations cb = new Combinations();
			List<Node> combOfClasses = cb.findCombination(listOfClasses, available, unitsMax, numOfElectiveUnits);
			
			return combOfClasses;   
    }
	
	public List<Node> getChildren(HashMap<String, ClassInfo> listOfClasses, List<String> classesTaken, int unitsMax, String semester, int year, boolean constraint, String name, String sem, String y) {
		
		Set<String> keySet = listOfClasses.keySet();
		List<String> allClasses = new ArrayList<String>(keySet);
		
		//find classes that are available to take next
			
			AvailableClasses av = new AvailableClasses(classesTaken, name, sem, year, y);
			List<String> available = av.checkAvailableClasses(allClasses, listOfClasses, semester, this.numOfElectiveUnits, constraint);
			this.availableClasses = available;
			
			//find all combination
			if(available.contains(name)){
				Combinations cb = new Combinations(name);
				List<Node> combOfClasses = cb.findCombination(listOfClasses, available, unitsMax, numOfElectiveUnits);
				return combOfClasses;
			}
			else{
				Combinations c = new Combinations();
				List<Node> combOfClasses = c.findCombination(listOfClasses, available, unitsMax, numOfElectiveUnits);
				return combOfClasses;
			}		
    }

    public List<String> getData() {
        return classTaken;
    }

    public void setData(List<String> data) {
        this.classTaken = data;
    }

    private void setParent(Node parent) {
        this.parent = parent;
    }

    public Node getParent() {
        return parent;
    }

    public int getNumOfElectiveUnits() {
		return numOfElectiveUnits;
	}

	public void addNumOfElectiveUnits(int numOfElectives) {
		this.numOfElectiveUnits += numOfElectives;
	}
	
	public void startPath(Node currentNode) {
		this.path.add(currentNode);
		setTakenClasses();
		
	}

	public void addToPath(Node currentNode, List<Node> pathNode) {
		for(Node n : pathNode) {
			this.path.add(n);
		}
		this.path.add(currentNode);
		setTakenClasses();
	}
	
	public List<Node> getPath(){
		return this.path;
	}
	
	private void setTakenClasses(){
		if(this.path.get(this.path.size() - 1).getData() == null) {//get the previous node from the path and get the list of  classes taken from that point on the path
			return;
		}
		for(Node n : this.path) {
			takenClassesFromPath.addAll(n.getData());
		}
	}
	
	public List<String> getTakenClasses(){
		return takenClassesFromPath;
	}
	
	public boolean isGoal() {
		return isGoal;
	}

	public void setGoal(boolean isGoal) {
		this.isGoal = isGoal;
	}

	public List<String> getAvailableClasses() {
		return availableClasses;
	}

	public void setAvailableClasses(List<String> availableClasses) {
		this.availableClasses = availableClasses;
	}

	public String getSemesterCode() {
		return semesterCode;
	}

	public void setSemesterCode(String semesterCode) {
		this.semesterCode = semesterCode;
	}

	public List<SemesterCourses> getSemesterCourses() {
		String[] semesters = {"Spring", "Fall"};
		int year = Year.now().getValue();
		String semesterCode = "";
		
		int index = 1;
		
		for(int i = 1; i < this.path.size(); i++) {
			//updates the index for the semester array to get the correct semester	
			semesterCode = semesters[index] + " " + year;
			this.path.get(i-1).getAvailableClasses().removeAll(this.path.get(i).getData());
			SemesterCourses sc = new SemesterCourses(semesterCode, this.path.get(i).getData(), this.path.get(i - 1).getAvailableClasses()); 
			semesterCourses.add(sc);
			System.out.println(sc);
			if(index % 2 == 0) { 
				index++;
				
			}
			else {//if its winter or spring, then new year starts
				year++;
				index = 0;
			}
		}
		return semesterCourses;
	}	
}