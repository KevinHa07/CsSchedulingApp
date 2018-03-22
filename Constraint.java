package BFS;

import java.util.Scanner;

public class Constraint {
	Scanner in = new Scanner(System.in);
	
	public Constraint(){
		System.out.println();
		System.out.println();
		System.out.println("Would you like to switch your classes? (yes/no): ");
		String option = in.nextLine();
		if(option.equals("yes")){
			System.out.println();
			System.out.println("What Class would you like to take and when ex(CS1010 Spring 2018): ");
			String[] option1 = in.nextLine().split(" ");
			
			if(isAvailable(option1[0], option1[1])){
				
				
			}else{
				System.out.println("You can not take this class that semester");
				Constraint c = new Constraint();
			}
			 
			
		}else{
			System.exit(0);
		}
	}
	
	public boolean isAvailable(String name, String semester){
		
//		if(){
			return true;
//			
//		}else{
//			return false;
//		}
		
		
	}

}



//In the constructor there will be a method that will check and make sure that class is available to take that semester
//If so go on, if not return an error statement
//Once in available class. When it gets time to the semester that the user wants, we will check and make sure that the class the user wants is in the available classes
//Once in combination class we will make sure that the only lists that get added to the combClass list are ones that have the user specified class in it