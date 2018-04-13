package BFS;

import java.time.Year;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class MakeTree {
	
	//test time
	public static long startTime = System.currentTimeMillis();
	private List<List<SemesterCourses>> listOfPaths = new ArrayList<>();
	String name;
	String semester;
	String y;
	
	//set the initial taken classes as parent node. Start the BFS. Go through the queue, the 
	//children of the element, remove the head, repeat

	public MakeTree() {
		
	}
	
	public void start(List<String> classesTaken, HashMap<String, ClassInfo> map, int unitsMax,
			boolean constraint, String name, String semester, String year) {
		this.name = name;
		this.semester = semester;
		this.y = year;
		
	}
	


	public List<SemesterCourses> start(List<String> classesTaken, HashMap<String, ClassInfo> listOfClassInfo, int unitsMax, boolean constraint) {
		Queue<Node> queue = new LinkedList<Node>();
		Set<List<String>> visited = new HashSet<List<String>>();
		List<SemesterCourses> sc = null;
		int currLevelSize = 0;
		int nextLevelSize = 0;
		int counter = 0;
		
		String[] semesters = {"Spring", "Fall"};
		int year = Year.now().getValue();
		
		int index = 1;
	

		//initial parent node
		Node parentNode = new Node(null);
		parentNode.setData(classesTaken);
		queue.add(parentNode); 
		parentNode.startPath(parentNode);	
		
		//start of BFS
		while(!queue.isEmpty()){

			Node curr = new Node(null);
			curr = queue.remove();
			
			//check if curr is in visited
			if(isVisited(visited, curr)){
				counter++;
				if(counter == nextLevelSize) {//counter is the the amount of children in the current level and if it equals to nextlevel, transition to next semester
					nextLevelSize = currLevelSize;
					currLevelSize = 0;
					counter = 0;
					//increment semesters
					if(index % 2 == 0) { 
						index++;
					}
					else {
						index = 0;
						year++;
					}
				}
				//if it is visited then we dont need to add the children again
				//the rest shall be skipped and the queue will move on to the next element
				
			}else{
				
				//check if curr is goal node
				if(checkGoal(curr)){			
					//if so print path
					sc = curr.getSemesterCourses();//list of semester courses for the current path
					listOfPaths.add(sc);
					
					long endTime = System.currentTimeMillis();
					long totaltime = endTime  - startTime;
					System.out.println(totaltime);
					System.out.println("");
					System.out.println("");

				}else{
					counter++;
					//add children to the path
					
					if(constraint){
						for( Node c : curr.getChildren(listOfClassInfo, curr.getTakenClasses(), unitsMax, semesters[index], year, constraint, name, semester, y)){
							curr.addChild(c);
							c.addToPath(c, curr.getPath());
							
							//get the children and add them to the queue
							queue.add(c);
							currLevelSize++;
						}
						
					}else{
						for( Node c : curr.getChildren(listOfClassInfo, curr.getTakenClasses(), unitsMax, semesters[index], year, constraint)){
							curr.addChild(c);
							c.addToPath(c, curr.getPath());
			
							//get the children and add them to the queue
							queue.add(c);
							currLevelSize++;
						}
						
					}
					
					if(nextLevelSize == 0) {//after getting the children for the first time set currlevel to nextlevel to keep track of level of when the semester should change
						nextLevelSize = currLevelSize;
						currLevelSize = 0;
						counter = 0;
						//increment semesters
						if(index % 2 == 0) { 
							index++;
							
						}
						else {
							index = 0;
							year++;
						}
					}
					else if(counter == nextLevelSize) {//counter is the the amount of children in the current level and if it equals to nextlevel, transition to next semester
						nextLevelSize = currLevelSize;
						currLevelSize = 0;
						counter = 0;
						//increment semesters
						if(index % 2 == 0) { 
							index++;
						}
						else {
							index = 0;
							year++;
						}
					}
				}
			}
		}
		
		return sc;
	}
	
	public List<List<SemesterCourses>> getListOfPaths(){
		return listOfPaths;
	}
	
	//visited is a set that keeps track of everything that goes through the queue. If there is a repeated element visited doesn't let the children go into the queue again
	public boolean isVisited(Set<List<String>> visited, Node curr){
		
		int pSize = curr.getPath().size();
		
		if( visited.contains(curr.getPath().get(pSize - 1).getData()) && !curr.getPath().get(pSize - 1).getData().isEmpty()){
			return true;
		}else{
			visited.add(curr.getPath().get(pSize - 1).getData());
			return false;
		}
	}
	
	//checks if a semester has cs4962 and cs4963
	public boolean checkGoal(Node curr){
		if(curr.getData().contains("CS4962") && curr.getData().contains("CS4963")){
			//if(curr.getData().contains("CS4961")){
			if(curr.getNumOfElectiveUnits() == 18){
				return true;
			}
			else{
				return false;
			}
			
		}else{
			
			return false;
		}
	}
}