package entities;

public class Patient {
	
	private int ID;
	public double PRICE;
	
	
	public Patient(int id, double price){
		
		ID = id;
		PRICE = price;
		
	}
	
	public int getID(){
		
		return ID;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
