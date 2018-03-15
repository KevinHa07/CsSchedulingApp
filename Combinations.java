package BFS;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Combinations {
	
	int maxUnit = 0;
	
	public List<Node> findCombination(HashMap<String, ClassInfo> listOfClasses, List<String> available, int maxUnits) {
		
		maxUnit = maxUnits;
		
		List<Node> combClasses = new ArrayList<Node>();
		
		//goes through the size of the available list to see what possible combinations there are
		//ex. if avail = a, b, c, d then it has to find comb with size = 1: a, b, c, d. size = 2: ab, ac, ad, bc, ... 
		//size = 3: abc, acd, bcd, ... size = 4: abcd
		for(int i = 0; i < available.size(); i++){
			int sizeOfavailList = available.size();
			int sizeOfNextCombo = i+1;
			
			printCombination(listOfClasses, available, sizeOfavailList, sizeOfNextCombo, combClasses);
		}
		
		return combClasses;
	}

	//this method creates a new temp ArrayList to store the new combinations in
	public void printCombination(HashMap<String, ClassInfo> listOfClasses, List<String> available, int sizeOfavailList, int sizeOfNextCombo, List<Node> combClasses){
	
		List<String> tempCombo = new ArrayList<String>(sizeOfNextCombo);
    	for(int i = 0; i < sizeOfNextCombo; i++) {
    		//with out this line we get an index out of bounce exception
    		tempCombo.add(null);
    	}
    	
		//check to see if combo should be added to list
		storeCombinations(listOfClasses, available, tempCombo, 0, sizeOfavailList - 1, 0, sizeOfNextCombo, combClasses);
	}
	
	public void storeCombinations(HashMap<String, ClassInfo> listOfClasses, List<String> available, List<String> tempCombo, int start, int end, int index, int sizeOfNextCombo,  List<Node> combClasses){
		
		//will start to add combinations to combClasses
		if (index == sizeOfNextCombo){
			
        	String temp = null;
        	List<String> tempList = new ArrayList<String>();
        	List <ClassInfo> classInfo = new ArrayList<ClassInfo>();
        	
            for (int j = 0; j < sizeOfNextCombo; j++){
               temp = tempCombo.get(j);
               tempList.add(temp);
               
               if(listOfClasses.containsKey(temp)) {
   						classInfo.add(listOfClasses.get(temp));
               }
            }
            
            //check to see if the combo fits in the desired unit preference
            int totalUnits = 0;
            for (int j = 0; j < classInfo.size(); j++){
            	totalUnits += classInfo.get(j).getUnits();
            }
            
            if(maxUnit >= totalUnits){
            	Node node = new Node(tempList);
            	combClasses.add(node);
			}
            
            //keep top 3
            for(int i = 0; combClasses.size() > 3;){
            	combClasses.remove(i);
            }

            return;
		}

		for (int i = start; i <= end; i++){
            tempCombo.set(index, available.get(i));
            //index + 1 replaces index with all possible elements
            storeCombinations(listOfClasses, available, tempCombo, i+1, end, index+1, sizeOfNextCombo, combClasses);
        }
	}
}