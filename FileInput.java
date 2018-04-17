package BFS;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class FileInput {
	private Map<String, ClassInfo> allClasses;
	private List<String> classNames;
	private List<ClassInfo> classes = new ArrayList<>();
	private File csvFile;
	
	public FileInput(String file) throws IOException {
		this.csvFile = new File(file);
		readCSV();
	}
	
	//reads csv file and prints it out
	private void readCSV() {
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        this.classNames = new ArrayList<String>();

        try {
            br = new BufferedReader(new FileReader(this.csvFile));
            while ((line = br.readLine()) != null) {
//            	System.out.println(line);
            	// use comma as separator
                String[] classInfo = line.split(cvsSplitBy);
                
                classInfo[0] = removeHyphen(classInfo[0]);
                this.classNames.add(classInfo[0]);
                classInfo[2] = removeHyphen(classInfo[2]);
                classInfo[3] = removeHyphen(classInfo[3]);
                addToClasses(classInfo);
            }
            

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
	}
	
	public String removeHyphen(String classString) {
		if(classString.equals(null)) {
			return "";
		}
		StringBuilder sb = new StringBuilder(classString);
        for(int i = 0; i < sb.length(); i++) {
        	if(sb.charAt(i) == '-') {
        		sb.deleteCharAt(i);
        		break;
        	}
        }
        return sb.toString();
	}
	
	public Map<String, ClassInfo> getListOfAllClasses(){
		for(int i = 0; i < classes.size(); i++) {
			allClasses.put(classNames.get(i), classes.get(i));
		}
		return allClasses;
	}
	
	public List<ClassInfo> getListOfClassInfo(){
		if(this.classes != null) {
			return this.classes;
		}
		return null;
	}
	
	public List<String> getListOfClassNames(){
		if(this.classes != null) {
			return this.classNames;
		}
		return null;
	}
	
	//puts the class info from csv file into an arraylist
	private void addToClasses(String[] classInfo) {
		ClassInfo ci = null;
		String delimeter = "/";
		List<String> offeredSemesters;
        if(classInfo[2].equals("") && classInfo[3].equals("")) {//if there are no prerequisites
        	if(classInfo[8].length() <= 1) {
        		offeredSemesters = new ArrayList<>(Arrays.asList(classInfo[8].split(",")));
        	}
        	else {
        		offeredSemesters = new ArrayList<>(Arrays.asList(classInfo[8].split(delimeter)));
        	}
        	ci = new ClassInfo(classInfo[0], Integer.parseInt(classInfo[1]), 
        			Integer.parseInt(classInfo[4]), classInfo[5], false, checkIsElective(classInfo[7]), offeredSemesters);
        	
        	this.classes.add(ci);
        	
        }
        else if(!classInfo[3].equals("") && classInfo[2].equals("")){//if there are no corequisites
        	String[] prerequisites;
        	if(classInfo[3].length() <= 1) {
        		prerequisites = classInfo[3].split(",");
        	}
        	else {
        		prerequisites = classInfo[3].split(delimeter);
        	}
        	
        	if(classInfo[8].length() <= 1) {
        		offeredSemesters = new ArrayList<>(Arrays.asList(classInfo[8].split(",")));
        	}
        	else {
        		offeredSemesters = new ArrayList<>(Arrays.asList(classInfo[8].split(delimeter)));
        	}

        	insertPrereqisites(classInfo, prerequisites, offeredSemesters);
        	
        }
        else if(classInfo[3].equals("") && !classInfo[2].equals("")){//if there are no prerequisites
        	String[] corequisites;
        	if(classInfo[2].length() <= 1) {
        		corequisites = classInfo[2].split(",");
        	}
        	else {
        		corequisites = classInfo[2].split(delimeter);
        	}
        	
        	if(classInfo[8].length() <= 1) {
        		offeredSemesters = new ArrayList<>(Arrays.asList(classInfo[8].split(",")));
        	}
        	else {
        		offeredSemesters = new ArrayList<>(Arrays.asList(classInfo[8].split(delimeter)));
        	}
        	insertCorequisites(classInfo, corequisites, offeredSemesters);
        	
        }
        else if(!classInfo[2].equals("") && !classInfo[3].equals("")){//if there are prerequisites and corequisites 
        	String[] prerequisites;
        	if(classInfo[3].length() <= 1) {
        		prerequisites = classInfo[3].split(",");
        	}
        	else {
        		prerequisites = classInfo[3].split(delimeter);
        	}
        	String[] corequisites;
        	if(classInfo[3].length() <= 1) {
        		corequisites = classInfo[2].split(",");
        	}
        	else {
        		corequisites = classInfo[2].split(delimeter);
        	}
        	
        	if(classInfo[8].length() <= 1) {
        		offeredSemesters = new ArrayList<>(Arrays.asList(classInfo[8].split(",")));
        	}
        	else {
        		offeredSemesters = new ArrayList<>(Arrays.asList(classInfo[8].split(delimeter)));
        	}
        	insertCorequisitesPrerequisites(classInfo, prerequisites, corequisites, offeredSemesters);
        }
        
	}
	
	//inserts prerequisite list to a classinfo object by comparing class name since the prerequisite list comes from csv file
	private void insertPrereqisites(String[] classInfo, String[] prerequisites, List<String> offeredSemesters) {
		ClassInfo ci;
		List<String> prereqs = new ArrayList<String>();
    	for(int i = 0; i < prerequisites.length; i++) { //sorts through the string of prereqs
    		for(int j = 0; j < classes.size(); j++) {//goes through all the classes and checks if class matches name of prereq
    			if(prerequisites[i].toLowerCase().equals(classes.get(j).getName().toLowerCase())){
    				prereqs.add(this.classes.get(j).getName());
    			}
    		}
    	}
    	ci = new ClassInfo(classInfo[0], Integer.parseInt(classInfo[1]), 
   				prereqs, Integer.parseInt(classInfo[4]), classInfo[5], false, checkIsElective(classInfo[7]), offeredSemesters); 
   		this.classes.add(ci);
    	
	}
	
	//inserts corequisites list to a classinfo object by comparing class name since the corequisite list comes from csv file
	private void insertCorequisites(String[] classInfo, String[] corequisites, List<String> offeredSemesters) {
		ClassInfo ci;
		List<String> coreqs = new ArrayList<String>();
    	for(int i = 0; i < corequisites.length; i++) { //sorts through the string of coreqs
    		for(int j = 0; j < this.classes.size(); j++) {
    			//need fix
    			//cant find class when its not in database yet
    			if(corequisites[i].toLowerCase().equals(this.classes.get(j).getName().toLowerCase())){//goes through all the classes and checks if class matches name of coreq
    				coreqs.add(this.classes.get(j).getName());
    			}
    		}
    	}
    	ci = new ClassInfo(classInfo[0], Integer.parseInt(classInfo[1]), coreqs, 
    			Integer.parseInt(classInfo[4]),  classInfo[5], false, checkIsElective(classInfo[7]), offeredSemesters);
   		this.classes.add(ci);
	}
	
	//inserts both corequisites and prerequisites list to a classinfo object by comparing class name since the both lists comes from csv file
	private void insertCorequisitesPrerequisites(String[] classInfo, String[] prerequisites, 
			 String[] corequisites, List<String> offeredSemesters) {
		ClassInfo ci;
		List<String> prereqs = new ArrayList<String>();
		List<String> coreqs = new ArrayList<String>();
    	for(int i = 0; i < prerequisites.length; i++) {//sorts through the string of prereqs
    		for(int j = 0; j < this.classes.size(); j++) {//goes through all the classes and checks if class matches name of prereq
    			if(prerequisites[i].toLowerCase().equals(this.classes.get(j).getName().toLowerCase())){
    				prereqs.add(this.classes.get(j).getName());
    			}
    		}
    	}
    	
    	for(int i = 0; i < corequisites.length; i++) {//sorts through the string of prereqs
    		for(int j = 0; j < this.classes.size(); j++) {//goes through all the classes and checks if class matches name of coreq
    			if(corequisites[i].toLowerCase().equals(this.classes.get(j).getName().toLowerCase())){
    				coreqs.add(this.classes.get(j).getName());
    			}
    		}
    	}
    	ci = new ClassInfo(classInfo[0], Integer.parseInt(classInfo[1]), 
    			coreqs, prereqs, Integer.parseInt(classInfo[4]), classInfo[5], false, checkIsElective(classInfo[7]), offeredSemesters);	
   		this.classes.add(ci); 	
	}
	
	//checks to see if a class is an elective
	private Boolean checkIsElective(String isElective) {
		if(isElective.equals("N")) {
			return false;
		}
		else {
			return true;
		}
	}
	
}



