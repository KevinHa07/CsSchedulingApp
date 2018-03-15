package BFS;

import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class MakeTree {
	
	//test time
	public static long startTime = System.currentTimeMillis();
	private List<List<SemesterCourses>> listOfPaths = new ArrayList<>();
	
	//set the initial taken classes as parent node. Start the BFS. Go through the queue, the 
	//children of the element, remove the head, repeat

	public MakeTree() {
		
	}
	
	public List<SemesterCourses> start(List<String> classesTaken, HashMap<String, ClassInfo> listOfClassInfo, int unitsMax) {
		Queue<Node> queue = new LinkedList<Node>();
		Set<List<String>> visited = new HashSet<List<String>>();
		List<SemesterCourses> sc = null;
		boolean breakWhile = false;
		int numberOfRoadMapsGenerated = 0;
		int amountOfRoadMaps = 3;
		int currLevelSize = 0;
		int nextLevelSize = 0;
		int counter = 0;
		
//		String[] semesters = {"Winter", "Spring", "Summer", "Fall"};
		String[] semesters = {"Spring", "Fall"};
//		int weekOfYear = Integer.parseInt(new SimpleDateFormat("w").format(new java.util.Date()));
		int year = Year.now().getValue();

		
		int index = 1;
		
//		if(weekOfYear >= 32 && weekOfYear < 51) {
//			index = 3;
//			semesterCode += semesters[index] + " " + year;
//		}
//		else if(weekOfYear >= 1 && weekOfYear < 3) {
//			index = 0;
//			semesterCode += semesters[index] + " " + ++year;
//		}
//		else if(weekOfYear >= 3 && weekOfYear < 21) {
//			index = 1;
//			semesterCode += semesters[index] + " " + ++year;
//		}
//		else if(weekOfYear >= 21 && weekOfYear < 32) {
//			index = 2;
//			semesterCode += semesters[index] + " " + ++year;
//		}
		
//		if(weekOfYear >= 21 && weekOfYear < 51) {
//			index = 1;
//		}
//		else if(weekOfYear >= 1 && weekOfYear < 21) {
//			index = 0;
//		}

		//initial parent node
		Node parentNode = new Node(null);
		parentNode.setData(classesTaken);
		
		queue.add(parentNode); 
		parentNode.startPath(parentNode);	
		
		while(!queue.isEmpty()){

			Node curr = new Node(null);
			curr = queue.remove();
			
			if(queue.size() == 2){
				System.out.println("");
			}
			
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
					//List<Node> path = curr.getPath();
					sc = curr.getSemesterCourses();//list of semester courses for the current path
					
					
					if(numberOfRoadMapsGenerated < amountOfRoadMaps) {//add path to roadmap
						numberOfRoadMapsGenerated++;
						listOfPaths.add(sc);
					}
					else {
						System.out.println("Number of paths: " + listOfPaths.size());
						for(List<SemesterCourses> c : listOfPaths) {
							System.out.println(c);
						}
						breakWhile = true;
					}
					
					long endTime = System.currentTimeMillis();
					long totaltime = endTime  - startTime;
					System.out.print(totaltime);
					
					//System.exit(0);

				}else{
					counter++;
//					System.out.println("Current Year: " + semesters[index] + " " + year + "   " + "queue size: " + queue.size() + "    curlev:" + currLevelSize + "    " +  counter + ":" + nextLevelSize);
					//add children to the path
					for( Node c : curr.getChildren(listOfClassInfo, curr.getTakenClasses(), unitsMax, semesters[index])){
						curr.addChild(c);
						c.addToPath(c, curr.getPath());
						
						//get the children and add them to the queue
						queue.add(c);
						currLevelSize++;
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
			
			if(breakWhile == true){
				break;
			}
		}
		
		return sc;
	}
	
	public List<List<SemesterCourses>> getListOfPaths(){
		return listOfPaths;
	}
	
	public boolean isVisited(Set<List<String>> visited, Node curr){
		
		int pSize = curr.getPath().size();
		
		if( visited.contains(curr.getPath().get(pSize - 1).getData()) && !curr.getPath().get(pSize - 1).getData().isEmpty()){
			return true;
		}else{
			visited.add(curr.getPath().get(pSize - 1).getData());
			return false;
		}
	}
	
	public boolean checkGoal(Node curr){
		if(curr.getData().contains("CS4962") && curr.getData().contains("CS4963")){
			return true;
		}else{
			
			return false;
		}
	}
	
}