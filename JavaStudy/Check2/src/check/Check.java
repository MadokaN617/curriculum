package check;

import constants.Constants;

public class Check {
	private static String firstName = "Madoka";
	private static String lastName = "Nishikura";
	
	private static void PrinterName (String firstName, String lastName) {
		System.out.println ("printNameメソッド"+ firstName + lastName);
	}
	
	public static void main( String[] args) {
			PrinterName(firstName, lastName);
			
		// TODO 自動生成されたメソッド・スタブ
			
			
		Pet pet = new Pet (Constants.CHECK_CLASS_JAVA, Constants.CHECK_CLASS_HOGE);
			pet.introduce();
		RobotPet robotPet = new RobotPet (Constants.CHECK_CLASS_R2D2, Constants.CHECK_CLASS_LUKE);
			robotPet.introduce();
	}
	

}
